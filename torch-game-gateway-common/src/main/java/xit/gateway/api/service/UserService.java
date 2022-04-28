package xit.gateway.api.service;

import reactor.core.publisher.Mono;
import xit.gateway.pojo.UserWithAuths;

public interface UserService {
    /**
     * 返回token
     * @return token
     */
    Mono<String> login(String username, String pwd);

    Mono<UserWithAuths> findUserWithAuthsByName(String username);
}
