package xit.gateway.utils;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import xit.gateway.constant.ResultCode;
import xit.gateway.pojo.ResultInfo;

import java.nio.charset.StandardCharsets;

import static xit.gateway.constant.ResultCode.OK;

public class RIUtils {
    private RIUtils(){}

    public static<T> Mono<ResultInfo<T>> create(ResultCode code, String msg, T data){
        return Mono.just(new ResultInfo<>(code.getValue(), msg, data));
    }

    public static<T> Mono<ResultInfo<T>> createOK(T data){
        return create(OK, null, data);
    }

    public static<T> Mono<ResultInfo<T>> createOK(){
        return createOK(null);
    }

    public static<T> Mono<Void> send(ServerHttpResponse response, ResultCode code, String msg, T data){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response.writeWith(Mono.just(response.bufferFactory()
                .wrap(JsonUtils.object2String(new ResultInfo<>(code.getValue(), msg, data))
                        .getBytes(StandardCharsets.UTF_8)
                )));
    }
}