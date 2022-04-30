package xit.gateway.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xit.gateway.admin.domain.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    void deleteAllByIdIn(Set<Long> ids);
}