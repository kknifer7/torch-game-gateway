package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("login")
    public Mono<ResultInfo<Object>> login() {
        Map<String, Object> authInfo = new HashMap<String, Object>(1) {{
            put("token", "token123456");
        }};
        return RIUtils.createOK(authInfo);
    }

    @PostMapping("logout")
    public Mono<ResultInfo<Object>> logout() {
        return RIUtils.createOK();
    }

    @GetMapping("info")
    public Mono<ResultInfo<Object>> info() {
        Map<String, Object> userInfo = new HashMap<String, Object>(2) {{
            put("userId", "1");
            put("username", "admin");
            put("password", "123456");
        }};

        return RIUtils.createOK(userInfo);
    }
}
