package xit.gateway.admin.repository;

import org.codehaus.commons.nullanalysis.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.Route;

import java.util.List;
import java.util.Set;

public interface RouteRepository extends JpaRepository<Route, String> {
    void deleteAllByIdIn(Set<String> ids);
}