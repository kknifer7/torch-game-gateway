package xit.gateway.core.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import xit.gateway.pojo.Config;

@Repository
public interface ConfigDAO extends ReactiveCrudRepository<Config, String> {
}
