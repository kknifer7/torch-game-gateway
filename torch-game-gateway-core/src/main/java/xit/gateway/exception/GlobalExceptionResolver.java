package xit.gateway.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.exception.requester.RouteDisabledException;
import xit.gateway.pojo.ResultMessage;
import xit.gateway.utils.ResultMessageUtils;

@RestControllerAdvice
public class GlobalExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(RequestFailedException.class)
    public ResultMessage handleException(RequestFailedException e){
        logger.error(e.getMessage(), e);
        // TODO 向Deacon提交调用失败信息
        return ResultMessageUtils.create(ResultMessage.ResultCode.REQUEST_FAILED, e.getMessage(), e.getRequestPath());
    }

    @ExceptionHandler(RouteDisabledException.class)
    public ResultMessage handlerException(RouteDisabledException e){
        logger.error(e.getMessage(), e);
        return ResultMessageUtils.create(ResultMessage.ResultCode.ROUTE_DISABLED, e.getMessage(), e.getRequestPath());
    }
}
