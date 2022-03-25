package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping("/val")
    public Mono<String> val(){
        return Mono.just("success");
    }
}
