package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping("/val/{param}")
    public Mono<String> val(@PathVariable("param") String param, ServerWebExchange exchange){
        exchange.getResponse().getHeaders().add("X-CSRF-TOKEN", "123456");
        return Mono.just("success - param=" + param);
    }

    @GetMapping("/val-form")
    public Mono<String> val0(@RequestParam(value = "param0", required = false) String param0, @RequestParam(value = "param1", required = false) String param1, @RequestBody String body){
        return Mono.just("success - param0=" + param0 + "&param1=" + param1 + " body: " + body);
    }
}
