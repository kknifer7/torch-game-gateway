package xit.gateway.deacon.cluster.heartbeatserver.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xit.gateway.api.cluster.heartbeatserver.HeartBeatServer;
import xit.gateway.pojo.Gateway;
import xit.gateway.utils.JsonUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultNettyHeartBeatServer implements HeartBeatServer {
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final int heartBeatIdleTimeout;
    private final int heartBeatServerPort;
    private final String password;

    public DefaultNettyHeartBeatServer(
            @Value("${torch.gateway.deacon.cluster.heart-beat-idle-timeout}")
                    int heartBeatIdleTimeout,
            @Value("${torch.gateway.deacon.cluster.heart-beat-server-port}")
                    int heartBeatServerPort,
            @Value("${torch.gateway.deacon.password}")
                    String password
    ) {
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(1);
        this.heartBeatIdleTimeout = heartBeatIdleTimeout;
        this.heartBeatServerPort = heartBeatServerPort;
        this.password = password;
    }

    @Override
    public void start() {
        new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LoggingHandler())
                                .addLast(new StringDecoder())
                                .addLast(new IdleStateHandler(
                                        heartBeatIdleTimeout,
                                        0, 0,
                                        TimeUnit.SECONDS)
                                )
                                .addLast(new DefaultHeartBeatServerHandler(password));
                    }
                })
                .bind(heartBeatServerPort);
    }

    private static class DefaultHeartBeatServerHandler extends ChannelInboundHandlerAdapter{
        private final String heartBeatPwd;

        public DefaultHeartBeatServerHandler(String heartBeatPwd) {
            this.heartBeatPwd = heartBeatPwd;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // password:gatewayInfo
            String[] heartBeatMsg = StringUtils.split(((String) msg), ':');
            Gateway gateway;

            if (validateHeartBeatMsg(heartBeatMsg)){
                // TODO 如果是新网关，保存网关信息
                gateway = JsonUtils.string2Object(heartBeatPwd, Gateway.class);
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
            // TODO 从网关容器中拿出网关信息，将其中的心跳断连属性+1，如果发现满足断开条件，则删除该条网关信息
            int currHeartBeatTime = 1;

            if (evt instanceof IdleStateEvent){
                IdleStateEvent event = (IdleStateEvent)evt;
                if (event.state()== IdleState.WRITER_IDLE && currHeartBeatTime < 5){
                    System.out.println("heart beat:  "+new Date());
                    ctx.writeAndFlush("booooop~ *" + ++currHeartBeatTime);
                    return;
                }
                TimeUnit.SECONDS.sleep(1000);
            }
        }
    }
}
