package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.GatewayUser;

import java.util.Set;

public interface GatewayUserRepository extends JpaRepository<GatewayUser, String> {

    GatewayUser findByUsername(String username);

    void deleteAllByIdIn(Set<String> ids);
}