package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.admin.service.GatewayUserService;
import xit.gateway.pojo.GatewayUser;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class GatewayUserController {

    @Resource
    private GatewayUserService gatewayUserService;

    @GetMapping("list")
    public Mono<ResultInfo<List<GatewayUser>>> list() {
        List<GatewayUser> userList = gatewayUserService.findAll();
        return RIUtils.createOK(userList);
    }

    @PostMapping("add")
    public Mono<ResultInfo<Object>> add(@RequestBody GatewayUser resources) {
        gatewayUserService.create(resources);
        return RIUtils.createOK();
    }

    @PostMapping("update")
    public Mono<ResultInfo<Object>> update(@RequestBody GatewayUser resources) {
        gatewayUserService.update(resources);
        return RIUtils.createOK();
    }

    @PostMapping("delete")
    public Mono<ResultInfo<Object>> delete(@RequestBody Set<Long> ids) {
        gatewayUserService.delete(ids);
        return RIUtils.createOK();
    }

}