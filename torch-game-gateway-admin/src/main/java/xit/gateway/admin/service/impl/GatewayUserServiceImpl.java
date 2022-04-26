package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.repository.GatewayUserRepository;
import xit.gateway.admin.service.GatewayUserService;
import xit.gateway.exception.requester.BadRequestException;
import xit.gateway.pojo.GatewayUser;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class GatewayUserServiceImpl implements GatewayUserService {

    private final GatewayUserRepository gatewayUserRepository;

    @Override
    public List<GatewayUser> findAll() {
        List<GatewayUser> userList = this.gatewayUserRepository.findAll();
        return userList;
    }

    @Override
    public void create(GatewayUser resources) {
        if (gatewayUserRepository.findByUsername(resources.getUsername()) != null) {
            throw new BadRequestException("用户已存在");
        }

        gatewayUserRepository.save(resources);
    }

    @Override
    public void update(GatewayUser resources) {
        GatewayUser user = gatewayUserRepository.findById(resources.getId()).orElseGet(GatewayUser::new);

        user.setUsername(resources.getUsername());
        if (resources.getPwd() != null) {
            user.setPwd(resources.getPwd());
        }

        gatewayUserRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        gatewayUserRepository.deleteAllByIdIn(ids);
    }
}

