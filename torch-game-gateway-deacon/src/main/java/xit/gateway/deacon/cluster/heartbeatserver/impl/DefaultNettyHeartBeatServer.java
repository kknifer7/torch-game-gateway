package xit.gateway.deacon.cluster.heartbeatserver.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xit.gateway.api.cluster.heartbeatserver.HeartBeatServer;
import xit.gateway.deacon.cluster.gateway.container.impl.GlobalGatewayContainer;
import xit.gateway.pojo.Gateway;
import xit.gateway.pojo.GatewayHeartBeatInfo;
import xit.gateway.utils.JsonUtils;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultNettyHeartBeatServer implements HeartBeatServer {
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final int heartBeatIdleTimeout;
    private final int heartBeatServerPort;
    private final String password;
    private final GlobalGatewayContainer gatewayContainer;

    @Autowired
    public DefaultNettyHeartBeatServer(
            @Value("${torch.gateway.deacon.heart-beat.idle-timeout}")
                    int heartBeatIdleTimeout,
            @Value("${torch.gateway.deacon.heart-beat.server-port}")
                    int heartBeatServerPort,
            @Value("${torch.gateway.deacon.password}")
                    String password,
            GlobalGatewayContainer gatewayContainer
    ) {
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(1);
        this.heartBeatIdleTimeout = heartBeatIdleTimeout;
        this.heartBeatServerPort = heartBeatServerPort;
        this.password = password;
        this.gatewayContainer = gatewayContainer;
    }

    @Override
    public void start() {
        new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new LoggingHandler())
                                .addLast(new StringDecoder())
                                .addLast(new IdleStateHandler(
                                        heartBeatIdleTimeout,
                                        0, 0,
                                        TimeUnit.SECONDS)
                                )
                                .addLast(new DefaultHeartBeatServerHandler(password, gatewayContainer));
                    }
                })
                .bind(heartBeatServerPort);
    }

    private static class DefaultHeartBeatServerHandler extends ChannelInboundHandlerAdapter{
        private final String heartBeatPwd;
        private final GlobalGatewayContainer gatewayContainer;
        private final static int DEAD_THRESHOLD = 5;
        private final Map<ChannelId, GatewayHeartBeatInfo> heartBeatInfoMap;

        public DefaultHeartBeatServerHandler(String heartBeatPwd, GlobalGatewayContainer gatewayContainer) {
            this.heartBeatPwd = heartBeatPwd;
            this.gatewayContainer = gatewayContainer;
            this.heartBeatInfoMap = new ConcurrentHashMap<>();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // password:gatewayInfo
            String[] heartBeatMsg = StringUtils.split(((String) msg), ":", 2);
            Channel clientChannel;
            InetSocketAddress clientAddress;
            Gateway gateway;

            if (validateHeartBeatMsg(heartBeatMsg)){
                gateway = JsonUtils.string2Object(heartBeatMsg[1], Gateway.class);
                clientChannel = ctx.channel();
                clientAddress = ((InetSocketAddress) clientChannel.remoteAddress());
                gateway.setHost(clientAddress.getHostName());
                if (!gatewayContainer.contains(gateway)){
                    // 首次收到心跳包，添加心跳统计信息用于记录失联次数
                    heartBeatInfoMap.put(clientChannel.id(), new GatewayHeartBeatInfo(gateway.getId(), 0));
                }
                gatewayContainer.put(gateway);
            }else{

                ctx.close();
            }

            super.channelRead(ctx, msg);
        }

        private boolean validateHeartBeatMsg(String[] heartBeatMsg){
            return heartBeatMsg.length == 2 && StringUtils.equals(heartBeatMsg[0], heartBeatPwd);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            Channel channel = ctx.channel();
            ChannelId channelId = channel.id();
            GatewayHeartBeatInfo heartBeatInfo = heartBeatInfoMap.get(channelId);
            int looseBeatTimes = heartBeatInfo.getLoseBeatTimes() + 1;

            if (looseBeatTimes >= DEAD_THRESHOLD){
                setGatewayDead(channelId);
                channel.close();
            }
            heartBeatInfo.setLoseBeatTimes(looseBeatTimes);
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            setGatewayDead(ctx.channel().id());
            super.handlerRemoved(ctx);
        }

        private void setGatewayDead(ChannelId channelId){
            // 网关实例已死，移除心跳信息和网关实例信息
            Optional.ofNullable(heartBeatInfoMap.remove(channelId))
                    .ifPresent(heartBeatInfo -> gatewayContainer.remove(heartBeatInfo.getGatewayId()));
        }
    }
}
