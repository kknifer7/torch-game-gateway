package xit.gateway.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xit.gateway.core.dao.GatewayUserDAO;

@Service
public class GatewayUserService implements ReactiveUserDetailsService {
    private final GatewayUserDAO gatewayUserDAO;

    @Autowired
    public GatewayUserService(GatewayUserDAO gatewayUserDAO) {
        this.gatewayUserDAO = gatewayUserDAO;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return gatewayUserDAO.findByUsername(username)
                .map(gatewayUser -> User.withUsername(username)
                        .password(gatewayUser.getPwd())
                        .authorities(StringUtils.split(gatewayUser.getAuthorities()))
                        .build()
                );
    }
}
