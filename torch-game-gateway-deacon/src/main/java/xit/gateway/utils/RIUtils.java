package xit.gateway.utils;

import xit.gateway.pojo.ResultInfo;

import static xit.gateway.constant.ResultCode.OK;

public class RIUtils {
    public static<T> ResultInfo<T> createOK(String msg){
        return new ResultInfo<>(OK, msg, null);
    }

    public static<T> ResultInfo<T> createOK(){
        return createOK(null);
    }
}
