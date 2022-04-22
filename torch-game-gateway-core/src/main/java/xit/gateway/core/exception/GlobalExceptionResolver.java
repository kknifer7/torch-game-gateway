package xit.gateway.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xit.gateway.core.constant.ResultCode;
import xit.gateway.core.exception.requester.RequestFailedException;
import xit.gateway.core.exception.route.RouteDisabledException;
import xit.gateway.core.pojo.ResultInfo;

@RestControllerAdvice
public class GlobalExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(RequestFailedException.class)
    public ResultInfo<String> handleException(RequestFailedException e){
        logger.error(e.getMessage(), e);
        // TODO 向Deacon提交调用失败信息
        return new ResultInfo<>(ResultCode.REQUEST_FAILED.getValue(), e.getMessage(), e.getRequestPath());
    }

    @ExceptionHandler(RouteDisabledException.class)
    public ResultInfo<String> handlerException(RouteDisabledException e){
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.ROUTE_DISABLED.getValue(), e.getMessage(), e.getRequestPath());
    }
}
