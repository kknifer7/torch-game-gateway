package xit.gateway.deacon.dao;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.Config;

@Repository
public interface ConfigDAO extends ReactiveCrudRepository<Config, Long> {
    @Query("select id, name, kee, val, remark from config where kee=:kee")
    Mono<Config> findByKee(String kee);
}
