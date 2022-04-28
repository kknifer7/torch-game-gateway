package xit.gateway.exception;

import io.jsonwebtoken.InvalidClaimException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xit.gateway.constant.ResultCode;
import xit.gateway.exception.requester.BadRequestException;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.exception.route.RouteDisabledException;
import xit.gateway.exception.route.RouteNotFoundException;
import xit.gateway.exception.user.UserVerificationFailedException;
import xit.gateway.pojo.ResultInfo;

@RestControllerAdvice
public class GlobalExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(RequestFailedException.class)
    public ResultInfo<String> handleException(RequestFailedException e) {
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.REQUEST_FAILED.getValue(), e.getMessage(), e.getRequestPath());
    }

    @ExceptionHandler(RouteDisabledException.class)
    public ResultInfo<String> handleException(RouteDisabledException e) {
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.ROUTE_DISABLED.getValue(), e.getMessage(), e.getRequestPath());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResultInfo<Object> badRequestException(BadRequestException e) {
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.BAD_REQUEST.getValue(), e.getMessage(), null);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public ResultInfo<String> handleException(RouteNotFoundException e){
        logger.error(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.REQUESTER_NOT_FOUND.getValue(), e.getMessage(), e.getRequestPath());
    }

    @ExceptionHandler(UserVerificationFailedException.class)
    public ResultInfo<Void> handleException(UserVerificationFailedException e){
        logger.warn(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.VERIFICATION_FAILED.getValue(), "账号或密码错误", null);
    }

    @ExceptionHandler(InvalidClaimException.class)
    public ResultInfo<Void> handlerException(InvalidClaimException e){
        logger.warn(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.FORBIDDEN.getValue(), "Token不合法", null);
    }
}
