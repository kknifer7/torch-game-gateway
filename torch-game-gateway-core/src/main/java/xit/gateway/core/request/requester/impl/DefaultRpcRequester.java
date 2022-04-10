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
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.core.loadbalancer.Loadbalancer;
import xit.gateway.core.request.requester.AbstractRequester;
import xit.gateway.core.request.requester.RpcRequester;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.pojo.Route;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class DefaultRpcRequester extends AbstractRequester implements RpcRequester {
    private final List<String> keys;
    private final Map<String, List<Route>> routes;
    private final Loadbalancer loadbalancer;
    private final Map<String, ChannelFuture> rpcChannels;
    private final Map<ChannelId, Promise<byte[]>> requestPromise;
    private final Logger logger = LoggerFactory.getLogger(DefaultRpcRequester.class);

    public DefaultRpcRequester(RouteGroup routeGroup, Loadbalancer loadbalancer) {
        this.routes = routeGroup.getRpcRoutes();
        this.loadbalancer = loadbalancer;
        this.keys = new CopyOnWriteArrayList<>();
        this.rpcChannels = new ConcurrentHashMap<>();
        this.requestPromise = new ConcurrentHashMap<>();
        if (!CollectionUtils.isEmpty(routes)){
            keys.addAll(routes.keySet());
        }
        connectServices();
    }

    private void connectServices(){
        // TODO 初始化连接所有RPC服务
        if (CollectionUtils.isEmpty(routes)) return;

        logger.debug("初始化连接所有RPC服务");
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        // 传输数据时将字节编码为字符串
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast(new StringEncoder())
                                .addLast(new DefaultRpcClientHandler());
                    }
                });

        routes.values().forEach(routeList -> routeList.forEach(route ->
                        bootstrap.remoteAddress(route.getHost(), route.getPort())
                                .connect()
                                .addListener((ChannelFuture future) -> rpcChannels.put(route.getId(), future))
        ));
    }

    @Override
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public Mono<Void> invoke(String routeName, ServerWebExchange exchange) {
        logger.debug("HTTP -> RPC");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        List<Route> routes = this.routes.get(routeName);
        // TODO 负载均衡
        Channel channel = rpcChannels.get(loadbalancer.choose(routes, requesterContext).getId()).channel();
        Promise<byte[]> resultPromise = new DefaultPromise<>(channel.eventLoop());

        requestPromise.put(channel.id(), resultPromise);

        return request.getBody().flatMap(dataBuffer -> {
            Mono<DataBuffer> res = null;

            channel.writeAndFlush(dataBuffer.toString(StandardCharsets.UTF_8));
            try {
                res = Mono.just(response.bufferFactory().wrap(resultPromise.get(10, TimeUnit.SECONDS)));
            }catch (InterruptedException | ExecutionException | TimeoutException e){
                e.printStackTrace();
            }
            response.setStatusCode(HttpStatus.OK);
            requestPromise.remove(channel.id());

            return response.writeWith(res).then();
        }).then();
    }

    private class DefaultRpcClientHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("连接建立");
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
