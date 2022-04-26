package xit.gateway.admin.service;


import xit.gateway.pojo.GatewayUser;

import java.util.List;
import java.util.Set;

public interface GatewayUserService {
    List<GatewayUser> findAll();


    void create(GatewayUser resources);


    void update(GatewayUser resources);

    void delete(Set<Long> ids);
}