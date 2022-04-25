package xit.gateway.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xit.gateway.constant.ResultCode;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.exception.route.RouteDisabledException;
import xit.gateway.pojo.ResultInfo;

@RestControllerAdvice
public class GlobalExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(RequestFailedException.class)
    public ResultInfo<String> handleException(RequestFailedException e){
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.REQUEST_FAILED.getValue(), e.getMessage(), e.getRequestPath());
    }

    @ExceptionHandler(RouteDisabledException.class)
    public ResultInfo<String> handlerException(RouteDisabledException e){
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.ROUTE_DISABLED.getValue(), e.getMessage(), e.getRequestPath());
    }
}
