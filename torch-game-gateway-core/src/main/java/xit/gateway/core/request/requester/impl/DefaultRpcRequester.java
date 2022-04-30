package xit.gateway.core.request.requester.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.api.request.requester.AbstractRequester;
import xit.gateway.api.request.requester.RpcRequester;
import xit.gateway.pojo.CallLog;
import xit.gateway.pojo.CalledRoute;
import xit.gateway.pojo.RequesterProxyResult;
import xit.gateway.pojo.Route;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.*;

public class DefaultRpcRequester extends AbstractRequester implements RpcRequester {
    private Channel channel;
    private final Map<ChannelId, Promise<byte[]>> requestPromise;
    private final Logger logger = LoggerFactory.getLogger(DefaultRpcRequester.class);

    public DefaultRpcRequester(Route route) {
        super(route);
        this.requestPromise = new ConcurrentHashMap<>();
        connectService();
    }

    private void connectService(){
        // 初始化连接RPC服务
        logger.debug("初始化连接RPC服务");
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .remoteAddress(route.getHost(), route.getPort())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        // 传输数据时将字节编码为字符串
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast(new StringEncoder())
                                .addLast(new DefaultRpcClientHandler());
                    }
                });

        // TODO 待开发：断开重连机制
        bootstrap.connect().addListener(future -> {
            if (!future.isSuccess()){
                logger.warn("RPC服务：{}，连接失败", route.getRemark());
                reconnect(group);
            }
        });
    }

    private void reconnect(EventLoopGroup group){
        group.schedule(() -> {
            logger.info("RPC服务：{}，尝试重连", route.getRemark());
            connectService();
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public RequesterProxyResult invoke(ServerWebExchange exchange) {
        if (!route.getStatus() || channel == null){
            throw new RequestFailedException("服务暂不可用", exchange.getRequest().getPath().value());
        }
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Promise<byte[]> resultPromise = new DefaultPromise<>(channel.eventLoop());
        RequesterProxyResult proxyResult = new RequesterProxyResult();
        Mono<Void> result;
        CallLog.Builder callRecordBuilder = CallLog.builder()
                .gatewayHost(request.getLocalAddress().getHostName())
                .gatewayPort(String.valueOf(request.getLocalAddress().getPort()))
                .gatewayUri(request.getPath().value())
                .serviceId(route.getServiceName())
                .timestamp(System.currentTimeMillis())
                .route(() -> {
                    CalledRoute calledRoute = new CalledRoute();
                    BeanUtils.copyProperties(route, calledRoute);

                    return calledRoute;
                });
        long startTime = System.currentTimeMillis();

        requestPromise.put(channel.id(), resultPromise);
        result = request.getBody().flatMap(dataBuffer -> {
            Mono<DataBuffer> res = null;

            channel.writeAndFlush(dataBuffer.toString(StandardCharsets.UTF_8));
            try {
                res = Mono.just(response.bufferFactory().wrap(resultPromise.get(10, TimeUnit.SECONDS)));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            callRecordBuilder.callTime(System.currentTimeMillis() - startTime);
            proxyResult.setCompleted(true);
            response.setStatusCode(HttpStatus.OK);
            requestPromise.remove(channel.id());
            if (res == null){
                res = Mono.just(response.bufferFactory().wrap("请求失败：服务不可用".getBytes(StandardCharsets.UTF_8)));
            }else{
                callRecordBuilder.success(true);
            }

            return response.writeWith(res).then();
        }).then();
        proxyResult.setCallRecord(callRecordBuilder.build());
        proxyResult.setResult(result);

        return proxyResult;
    }

    private class DefaultRpcClientHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("RPC服务：{}，连接成功", route.getRemark());
            channel = ctx.channel();
            super.channelActive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Promise<byte[]> channelPromise = requestPromise.get(ctx.channel().id());

            logger.debug("接收RPC结果");
            channelPromise.setSuccess((ByteBufUtil.getBytes((ByteBuf) msg)));
            super.channelRead(ctx, msg);
        }
    }
}
