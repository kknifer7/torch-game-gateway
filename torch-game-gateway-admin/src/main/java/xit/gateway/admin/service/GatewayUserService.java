package xit.gateway.admin.service;


import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.GatewayUser;

import java.util.List;
import java.util.Set;

public interface GatewayUserService {
    List<GatewayUser> findAll();


    void create(GatewayUser resources);


    void update(GatewayUser resources);

    void delete(Set<String> ids);

}