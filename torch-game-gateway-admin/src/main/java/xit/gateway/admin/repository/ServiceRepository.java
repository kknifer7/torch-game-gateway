package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.Service;
import xit.gateway.admin.domain.User;

import java.util.Set;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findByName(String name);

    void deleteAllByIdIn(Set<Long> ids);
}