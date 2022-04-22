package xit.gateway.deacon.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import xit.gateway.core.pojo.Config;

@Repository
public interface ConfigDAO extends ReactiveCrudRepository<Config, String> {
}
