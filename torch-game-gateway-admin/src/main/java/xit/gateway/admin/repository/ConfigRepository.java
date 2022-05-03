package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.Config;
import xit.gateway.admin.domain.User;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findByName(String name);
}