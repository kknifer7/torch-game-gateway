package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.pojo.GatewayUser;
import xit.gateway.pojo.Route;

import java.util.Optional;
import java.util.Set;

public interface RouteRepository extends JpaRepository<Route, String> {

    Route findByName(String name);

    void deleteAllByIdIn(Set<Long> ids);

}