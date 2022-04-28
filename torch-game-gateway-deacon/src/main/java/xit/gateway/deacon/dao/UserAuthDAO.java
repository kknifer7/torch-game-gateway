package xit.gateway.deacon.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import xit.gateway.pojo.UserAuth;

@Repository
public interface UserAuthDAO extends ReactiveCrudRepository<UserAuth, Long> {
    @Query("select user_id, auth from user_auth where user_id=:userId")
    Flux<UserAuth> findByUserId(Long userId);
}
