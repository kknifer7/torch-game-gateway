package xit.gateway.core.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.ResultInfo;

/**
 * @author Knifer
 * Description: 负责处理当前实例的变更任务
 * Date: 2022/04/13
 */
// TODO 待完成：配置文件中的密文验证
@RestController
@RequestMapping("/action")
public class ActionController {



    @PostMapping("/sync-route/{serviceName}/{routeId}")
    public Mono<ResultInfo<Void>> syncRoute(
            @PathVariable("serviceName") String serviceName,
            @PathVariable("routeId") String routeId
    ){
        // TODO 如果发现项目中已有该路由，则修改该路由条目
        // TODO 如果发现项目中没有该路由，则挂载该路由条目
        // 从Redis中获取要同步的路由条目
        return null;
    }

    @PostMapping("/sync-route-group/{routeGroupId}")
    public Mono<ResultInfo<Void>> syncRouteGroup(){
        return null;
    }
}
