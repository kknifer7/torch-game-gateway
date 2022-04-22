package xit.gateway.utils;

import reactor.core.publisher.Mono;
import xit.gateway.core.constant.ResultCode;
import xit.gateway.core.pojo.ResultInfo;

import static xit.gateway.core.constant.ResultCode.OK;

public class RIUtils {
    public static<T> Mono<ResultInfo<T>> createOK(String msg){
        return Mono.just(new ResultInfo<>(OK.getValue(), msg, null));
    }

    public static<T> Mono<ResultInfo<T>> createOK(){
        return createOK(null);
    }

    public static<T> Mono<ResultInfo<T>> create(ResultCode code, String msg, T data){
        return Mono.just(new ResultInfo<>(code.getValue(), msg, data));
    }
}