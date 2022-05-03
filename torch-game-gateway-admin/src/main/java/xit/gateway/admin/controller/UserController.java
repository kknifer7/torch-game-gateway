package xit.gateway.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import xit.gateway.admin.domain.User;
import xit.gateway.admin.service.UserService;
import xit.gateway.admin.service.dto.UserLimitDto;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${torch.gateway.core.url}")
    private String domain;

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
    public Mono<ResultInfo<Object>> delete(@RequestBody Map<String, Long> dto) {
        userService.delete(dto.get("id"));
        return RIUtils.createOK();
    }


    @PostMapping("limit")
    public ResultInfo limit(@RequestBody UserLimitDto dto, @RequestHeader HttpHeaders headers) {
        HashMap<String, Object> requestData = new HashMap<>() {{
            put("userId", dto.getUserId());
            put("limit", dto.getLimit());
            put("limitingTimeout", dto.getLimitingTimeout());
            put("limitingTimeUnit", dto.getLimitingTimeUnit());
        }};
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>((Map<String, Object>) requestData, headers);

        return restTemplate.postForObject(domain + "/action/admin/add-limiter-for-user", request, ResultInfo.class);
    }
}