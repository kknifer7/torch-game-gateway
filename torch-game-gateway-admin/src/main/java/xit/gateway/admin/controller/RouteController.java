package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.admin.domain.Route;
import xit.gateway.admin.service.RouteService;
import xit.gateway.constant.RedisChannel;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;
import xit.gateway.utils.RedisUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Resource
    private RouteService routeService;

    @GetMapping("list")
    public Mono<ResultInfo<List<Route>>> list() {
        List<Route> routeList = routeService.findAll();

        return RIUtils.createOK(routeList);
    }

    @PostMapping("add")
    public Mono<ResultInfo<Object>> add(@RequestBody Route resources) {
        Route route = routeService.create(resources);

        RedisUtils.publish(RedisChannel.ROUTE, route);
        return RIUtils.createOK();
    }

    @PostMapping("update")
    public Mono<ResultInfo<Object>> update(@RequestBody Route resources) {
        Route route = routeService.update(resources);
        RedisUtils.publish(RedisChannel.ROUTE, route);
        return RIUtils.createOK();
    }

    @PostMapping("delete")
    public Mono<ResultInfo<Object>> delete(@RequestBody Map<String, String> dto) {
        String id = dto.get("id");
        Optional<Route> route = routeService.findById(id);

        routeService.delete(id);

        RedisUtils.publish(RedisChannel.ROUTE_DELETE, route);
        return RIUtils.createOK();
    }

    @GetMapping("sync")
    public Mono<ResultInfo<Object>> sync() {
        List<Route> routeList = routeService.findAll();

        RedisUtils.publish(RedisChannel.ROUTE_LIST, routeList);
        return RIUtils.createOK();
    }
}
