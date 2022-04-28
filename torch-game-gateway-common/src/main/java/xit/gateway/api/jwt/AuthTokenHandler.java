package xit.gateway.api.jwt;

import xit.gateway.pojo.AuthToken;

/**
 * @author Knifer
 * Date: 2022/04/27
 * Description: 登录Token的Creator
 */
public interface AuthTokenHandler {
    String newToken(Long userId);
    AuthToken parse(String token);
}
