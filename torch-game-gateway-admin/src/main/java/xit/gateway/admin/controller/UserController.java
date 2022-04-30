package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.admin.service.UserService;
import xit.gateway.admin.domain.User;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("list")
    public Mono<ResultInfo<List<User>>> list() {
        List<User> userList = userService.findAll();
        return RIUtils.createOK(userList);
    }

    @PostMapping("add")
    public Mono<ResultInfo<Object>> add(@RequestBody User resources) {
        userService.create(resources);
        return RIUtils.createOK();
    }

    @PostMapping("update")
    public Mono<ResultInfo<Object>> update(@RequestBody User resources) {
        userService.update(resources);
        return RIUtils.createOK();
    }

    @PostMapping("delete")
    public Mono<ResultInfo<Object>> delete(@RequestBody Set<Long> ids) {
        userService.delete(ids);
        return RIUtils.createOK();
    }

}