package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.Route;

import java.util.Set;

public interface RouteRepository extends JpaRepository<Route, String> {

    Route findByName(String name);

    void deleteAllByIdIn(Set<String> ids);

}