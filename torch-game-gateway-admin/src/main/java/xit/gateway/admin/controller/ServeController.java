package xit.gateway.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xit.gateway.admin.service.HTTPService;
import xit.gateway.pojo.ResultInfo;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/serve")
public class ServeController {
    @Autowired
    private HTTPService httpService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("stat")
    public ResultInfo stat(@RequestHeader HttpHeaders headers) {
        return httpService.post("/list-gateways", headers, new HashMap<>());
    }

    @PostMapping("limit/list")
    public ResultInfo limitList(@RequestBody Map<String, Object> map, @RequestHeader HttpHeaders headers) {
        String apiURL = "http://" + map.get("domain") + "/action/admin/list-limiters";

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(new HashMap<String, Object>() {
        }, headers);
        ResultInfo resultInfo = restTemplate.postForObject(apiURL, request, ResultInfo.class);
        return resultInfo;
    }

    @PostMapping("limit/delete")
    public ResultInfo limitDelete(@RequestBody Map<String, Object> map, @RequestHeader HttpHeaders headers) {
        String apiURL = "http://" + map.get("domain") + "/action/admin/remove-limiter-for-user/"+ map.get("userId");

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(new HashMap<String, Object>() {
        }, headers);
        ResultInfo resultInfo = restTemplate.postForObject(apiURL, request, ResultInfo.class);
        return resultInfo;
    }


    @PostMapping("route/list")
    public ResultInfo routeList(@RequestBody Map<String, Object> map, @RequestHeader HttpHeaders headers) {
        String apiURL = "http://" + map.get("domain") + "/action/admin/list-routes";

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(new HashMap<String, Object>() {
        }, headers);
        ResultInfo resultInfo = restTemplate.postForObject(apiURL, request, ResultInfo.class);
        return resultInfo;
    }

}
