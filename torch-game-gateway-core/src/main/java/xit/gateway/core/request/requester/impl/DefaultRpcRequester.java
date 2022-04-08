package xit.gateway.core.request.requester.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.core.request.requester.RpcRequester;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.pojo.Route;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class DefaultRpcRequester implements RpcRequester {
    private final List<String> keys;
    private final Map<String, List<Route>> routes;
    private final Map<String, ChannelFuture> rpcChannels;
    private final Map<ChannelId, Promise<String>> requestPromise;

    public DefaultRpcRequester(RouteGroup routeGroup) {
        this.routes = routeGroup.getRpcRoutes();
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

        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 传输数据时将字节编码为字符串
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new StringEncoder())
                                .addLast(new DefaultRpcClientHandler());
                    }
                });
                //.handler(new DefaultRpcClientHandler());

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
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        List<Route> routes = this.routes.get(routeName);
        // TODO 负载均衡
        Channel channel = rpcChannels.get(routes.get(0).getId()).channel();
        Promise<String> resultPromise = new DefaultPromise<>(channel.eventLoop());

        requestPromise.put(channel.id(), resultPromise);
        request.getBody().map(dataBuffer -> {
            // 写向目标服务
            channel.write(dataBuffer);
            try {
                // 获取返回值并添加到HTTP Response
                response.writeWith(
                        Mono.just(response.bufferFactory()
                                .wrap(resultPromise.get(3, TimeUnit.SECONDS).getBytes())
                ));
            } catch (ExecutionException | TimeoutException | InterruptedException e) {
                throw new RequestFailedException(e.getMessage());
            }

            return dataBuffer;
        });

        return Mono.empty();
    }

    private class DefaultRpcClientHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg){
            Promise<String> channelPromise = requestPromise.get(ctx.channel().id());

            channelPromise.setSuccess((String) msg);
            ctx.fireChannelRead(msg);
        }
    }
}
