package xit.gateway.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import xit.gateway.admin.domain.User;
import xit.gateway.admin.service.UserService;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.JsonUtils;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@SuppressWarnings("all")
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private UserService userService;

    @PostMapping("login")
    public ResultInfo login(@RequestBody Map<String, Object> map) {
        String apiURL = "http://" + map.get("host") + ":" + map.get("port") + "/login";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestParam = new HashMap<String, Object>(){{
            put("username", map.get("username"));
            put("pwd", map.get("pwd"));
        }};

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(requestParam, headers);
        ResponseEntity<String> entity = restTemplate.exchange(apiURL, HttpMethod.POST, request, String.class);
        String body = entity.getBody();
        ResultInfo resultInfo = JsonUtils.string2Object(body, ResultInfo.class);
         return resultInfo;
    }

    @PostMapping("logout")
    public Mono<ResultInfo<Object>> logout() {
        return RIUtils.createOK();
    }

    @GetMapping("info")
    public Mono<ResultInfo<Object>> info() {
        Long id = 1l;
        Optional<User> user = userService.findOne(id);
        return RIUtils.createOK(user);
    }
}
