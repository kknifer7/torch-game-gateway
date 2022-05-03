package xit.gateway.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xit.gateway.admin.service.HTTPService;
import xit.gateway.pojo.ResultInfo;

import java.util.HashMap;

@RestController
@RequestMapping("/serve")
public class ServeController {
    @Autowired
    private HTTPService httpService;

    @GetMapping("stat")
    public ResultInfo stat(@RequestHeader HttpHeaders headers) {
        return httpService.post("/list-gateways", headers, new HashMap<>());
    }
}
