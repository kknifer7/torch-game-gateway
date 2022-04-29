package xit.gateway.deacon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xit.gateway.api.jwt.AuthTokenHandler;
import xit.gateway.api.service.UserService;
import xit.gateway.constant.RedisKey;
import xit.gateway.deacon.dao.UserAuthDAO;
import xit.gateway.deacon.dao.UserDAO;
import xit.gateway.exception.user.UserNotFoundException;
import xit.gateway.exception.user.UserVerificationFailedException;
import xit.gateway.pojo.UserAuth;
import xit.gateway.pojo.UserWithAuths;
import xit.gateway.utils.RedisUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {
    private final UserDAO userDAO;
    private final UserAuthDAO userAuthDAO;
    private final AuthTokenHandler tokenCreator;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserAuthDAO userAuthDAO, AuthTokenHandler tokenCreator) {
        this.userDAO = userDAO;
        this.userAuthDAO = userAuthDAO;
        this.tokenCreator = tokenCreator;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userDAO.findByUsername(username)
                .flatMap(user -> userAuthDAO.findByUserId(user.getId())
                        .collectList()
                        .map(userAuth -> User.withUsername(username)
                                .password(user.getPwd())
                                .authorities(
                                        userAuth.stream()
                                                .map(UserAuth::getAuth)
                                                .toArray(String[]::new)
                                )
                                .build()
                        )
                );
    }

    @Override
    public Mono<String> login(String username, String pwd) {
        return Optional.ofNullable(findUserWithAuthsByUsername(username))
                .orElseThrow(UserNotFoundException::new)
                .map(user -> {
                    if (BCrypt.checkpw(pwd, user.getPwd())) {
                        Long id = user.getId();

                        RedisUtils.set(RedisKey.USER.extend(id), user);

                        return tokenCreator.newToken(user.getId());
                    } else {
                        throw new UserVerificationFailedException("账号或密码错误");
                    }
                });
    }

    @Override
    public Mono<UserWithAuths> findUserWithAuthsByUsername(String username) {
        return userDAO.findByUsername(username)
                .flatMap(user -> userAuthDAO.findByUserId(user.getId())
                        .collectList()
                        .map(userAuth -> new UserWithAuths(
                                user.getId(),
                                user.getUsername(),
                                user.getPwd(),
                                userAuth.stream()
                                        .map(UserAuth::getAuth)
                                        .collect(Collectors.toSet()),
                                user.getCreatedAt(),
                                user.getUpdatedAt()
                        ))
                );
    }
}
