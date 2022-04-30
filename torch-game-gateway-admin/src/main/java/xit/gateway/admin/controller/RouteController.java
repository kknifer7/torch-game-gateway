package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.admin.service.RouteService;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.admin.domain.Route;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        routeService.create(resources);
        return RIUtils.createOK();
    }

    @PostMapping("update")
    public Mono<ResultInfo<Object>> update(@RequestBody Route resources) {
        routeService.update(resources);
        return RIUtils.createOK();
    }

    @PostMapping("delete")
    public Mono<ResultInfo<Object>> delete(@RequestBody Set<String> ids) {
        routeService.delete(ids);
        return RIUtils.createOK();
    }


}
