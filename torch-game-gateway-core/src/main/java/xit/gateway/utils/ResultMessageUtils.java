package xit.gateway.utils;

import xit.gateway.pojo.ResultMessage;

public class ResultMessageUtils {
    private ResultMessageUtils(){}

    public static ResultMessage create(ResultMessage.ResultCode code, String msg, String path){
        return new ResultMessage(code.getValue(), msg, path, System.currentTimeMillis());
    }
}
