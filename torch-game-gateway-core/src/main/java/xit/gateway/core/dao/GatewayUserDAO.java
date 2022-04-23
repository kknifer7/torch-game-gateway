package xit.gateway.core.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.GatewayUser;

@Repository
public interface GatewayUserDAO extends ReactiveCrudRepository<GatewayUser, String> {
    @Query("select * from gateway_user where username=:username")
    Mono<GatewayUser> findByUsername(String username);
}
