package xit.gateway.admin.service;


import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findOne(Long id);

    List<User> findAll();

    void create(User resources);


    void update(User resources);

    @Transactional(rollbackFor = Exception.class)
    void delete(Long id);
}