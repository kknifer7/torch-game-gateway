package xit.gateway.core.controller;

import io.jsonwebtoken.lang.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.api.service.UserService;
import xit.gateway.constant.ResultCode;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import java.util.Map;

@RestController
public class SecurityController {

    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Mono<ResultInfo<Map<String,String>>> login(@RequestBody Map<String, String> usernameAndPassword){

        return userService.login(usernameAndPassword.get("username"), usernameAndPassword.get("pwd"))
                .flatMap(token -> Mono.just(new ResultInfo<>(ResultCode.OK.getValue(), "success", Maps.of("token", token).build())))
                .switchIfEmpty(RIUtils.create(ResultCode.VERIFICATION_FAILED, "账号或密码错误", null));
    }
}
