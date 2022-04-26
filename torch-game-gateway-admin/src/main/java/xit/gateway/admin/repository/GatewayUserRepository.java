package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.pojo.GatewayUser;

import java.util.Set;

public interface GatewayUserRepository extends JpaRepository<GatewayUser, Long> {

    GatewayUser findByUsername(String username);

    void deleteAllByIdIn(Set<Long> ids);
}