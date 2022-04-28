package xit.gateway.core.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.User;

@Repository
public interface UserDAO extends ReactiveCrudRepository<User, String> {
    @Query("select * from user where username=:username")
    Mono<User> findByUsername(String username);
}
