package xit.gateway.core.cluster.heartbeatclient.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xit.gateway.api.cluster.heartbeatclient.HeartBeatClient;
import xit.gateway.api.context.GatewayContext;
import xit.gateway.pojo.Gateway;
import xit.gateway.utils.JsonUtils;
import xit.gateway.utils.OshiUtils;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultNettyHeartBeatClient implements HeartBeatClient {
    private final String serverHost;
    private final int serverPort;
    private final String serverPwd;
    private final Gateway gateway;
    private final Logger logger = LoggerFactory.getLogger(DefaultNettyHeartBeatClient.class);

    public DefaultNettyHeartBeatClient(
            GatewayContext gatewayContext,
            @Value("${torch.gateway.call-trace.deacon.heart-beat.host}")
            String serverHost,
            @Value("${torch.gateway.call-trace.deacon.heart-beat.port}")
            int serverPort,
            @Value("${torch.gateway.call-trace.deacon.password}")
            String serverPwd
    ){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.serverPwd = serverPwd;
        this.gateway = gatewayContext.gateway();
    }

    @Override
    public void startBeat() {
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) {
                        channel.pipeline()
                                .addLast(new IdleStateHandler(
                                        0, 5, 0,
                                        TimeUnit.SECONDS
                                ))
                                .addLast(new StringEncoder())
                                .addLast(new DefaultHeatBeatClientHandler(serverPwd, gateway));
                    }
                })
                .remoteAddress(new InetSocketAddress(serverHost, serverPort))
                .connect().addListener(future -> {
                    if (!future.isSuccess()){
                        logger.warn("[{}]连接失败，尝试重连...", serverHost + ":" + serverPort);
                        reconnect(group);
                    }
                });
    }

    private void reconnect(EventLoopGroup group){
        group.schedule(this::startBeat, 5, TimeUnit.SECONDS);
    }

    private static class DefaultHeatBeatClientHandler extends ChannelInboundHandlerAdapter{
        private final String serverPwd;
        private final Gateway gateway;

        public DefaultHeatBeatClientHandler(String serverPwd, Gateway gateway){
            this.serverPwd = serverPwd;
            this.gateway = gateway;
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent){
                IdleStateEvent event = (IdleStateEvent)evt;
                if (event.state() == IdleState.WRITER_IDLE){
                    ctx.writeAndFlush(packageHeartBeatInfo());
                }
            }
            super.userEventTriggered(ctx, evt);
        }

        private String packageHeartBeatInfo(){
            OshiUtils.SimpleMemoryInfo simpleMemoryInfo = OshiUtils.getSimpleMemoryInfo();
            OshiUtils.SimpleCPUInfo simpleCPUInfo = OshiUtils.getSimpleCPUInfo();

            gateway.setCpuCores(simpleCPUInfo.getLogicalProcessorCount());
            gateway.setCpuSys(simpleCPUInfo.getCpuUsed());
            gateway.setTotalMemory(simpleMemoryInfo.getTotal());
            gateway.setFreeMemory(simpleMemoryInfo.getAvailable());
            gateway.setCreateAt(LocalDateTime.now());

            return serverPwd + ":" + JsonUtils.object2String(gateway);
        }
    }
}
