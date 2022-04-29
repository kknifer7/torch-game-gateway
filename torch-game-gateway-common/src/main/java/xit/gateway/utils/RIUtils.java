package xit.gateway.utils;

import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import xit.gateway.constant.ResultCode;
import xit.gateway.pojo.ResultInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        return response.writeWith(Mono.just(response.bufferFactory()
                .wrap(JsonUtils.object2String(new ResultInfo<>(code.getValue(), msg, data))
                        .getBytes(StandardCharsets.UTF_8)
                )));
    }

    public static<T> void send(HttpServletResponse response, ResultCode code, String msg, T data){
        try {
            response.getWriter().print(JsonUtils.object2String(new ResultInfo<>(code.getValue(), msg, data)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}