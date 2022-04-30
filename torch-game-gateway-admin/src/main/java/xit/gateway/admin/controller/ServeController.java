package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

@RestController
@RequestMapping("/serve")
public class ServeController {

    @GetMapping("stat")
    public Mono<ResultInfo<Object>> stat() {

        return RIUtils.createOK(null);
    }
}
