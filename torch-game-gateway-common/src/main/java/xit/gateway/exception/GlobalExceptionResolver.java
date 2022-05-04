package xit.gateway.exception;

import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import xit.gateway.constant.ResultCode;
import xit.gateway.exception.requester.BadRequestException;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.exception.route.RouteDisabledException;
import xit.gateway.exception.route.RouteNotFoundException;
import xit.gateway.exception.system.SystemException;
import xit.gateway.exception.user.AccessForbiddenException;
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

    @ExceptionHandler({BadRequestException.class, UnsupportedMediaTypeStatusException.class})
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

    @ExceptionHandler({InvalidClaimException.class, SignatureException.class})
    public ResultInfo<Void> handleException(InvalidClaimException e){
        logger.warn(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.FORBIDDEN.getValue(), "Token不合法", null);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ResultInfo<Void> handleException(AccessForbiddenException e){
        logger.warn(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.FORBIDDEN.getValue(), "请登录", null);
    }

    @ExceptionHandler(SystemException.class)
    public ResultInfo<Void> handleException(SystemException e){
        logger.warn(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.SYSTEM_ERROR.getValue(), "网关业务层无法受理你的请求：参数非法", null);
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo<Void> handleException(Exception e){
        logger.warn(e.getMessage(), e);
        return new ResultInfo<>(ResultCode.SYSTEM_ERROR.getValue(), "系统异常", null);
    }
}
