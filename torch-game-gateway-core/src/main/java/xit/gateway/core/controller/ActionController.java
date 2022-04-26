package xit.gateway.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.api.service.RouteService;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

/**
 * @author Knifer
 * Description: 负责处理当前实例的变更任务
 * Date: 2022/04/13
 */
// TODO 待完成：配置文件中的密文验证
@RestController
@RequestMapping("/action")
public class ActionController {

    private final RouteService routeService;

    @Autowired
    public ActionController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/add-service/{serviceName}")
    public Mono<ResultInfo<Void>> addService(@PathVariable("serviceName") String serviceName){
        routeService.addRoutesFromRedis(serviceName);
        return RIUtils.createOK();
    }

    @PostMapping("/sync-route/{serviceName}/{routeId}")
    public Mono<ResultInfo<Void>> syncRoute(
            @PathVariable("serviceName") String serviceName,
            @PathVariable("routeId") String routeId
    ){
        // 从Redis中获取要同步的路由条目

        return null;
    }

    @PostMapping("/sync-route-group/{routeGroupId}")
    public Mono<ResultInfo<Void>> syncRouteGroup(){
        return null;
    }
}
