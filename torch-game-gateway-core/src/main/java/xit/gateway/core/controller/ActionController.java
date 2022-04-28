package xit.gateway.core.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.api.service.RouteService;
import xit.gateway.constant.ResultCode;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;


/**
 * @author Knifer
 * Description: 负责处理当前实例的变更任务
 * Date: 2022/04/13
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    private final RouteService routeService;
    private final String password;

    @Autowired
    public ActionController(
            RouteService routeService,
            @Value("${torch.gateway.password}") String password
    ) {
        this.routeService = routeService;
        this.password = password;
    }

    @PostMapping("/admin/add-service/{serviceId}")
    public Mono<ResultInfo<Void>> addService(@PathVariable("serviceId") String serviceId){
        routeService.addRoutesFromRedis(serviceId);

        return RIUtils.createOK();
    }


    @PostMapping("/admin/add-route/{serviceId}/{routeId}")
    public Mono<ResultInfo<Void>> addRoute(
            @PathVariable("serviceId") String serviceId,
            @PathVariable("routeId") String routeId
    ){
        routeService.addRouteFromRedis(serviceId, routeId);

        return RIUtils.createOK();
    }

    @PostMapping("/admin/update-route/{serviceId}/{routeId}")
    public Mono<ResultInfo<Void>> updateRoute(
            @PathVariable("serviceId") String serviceId,
            @PathVariable("routeId") String routeId
    ){
        routeService.updateRouteFromRedis(serviceId, routeId);

        return RIUtils.createOK();
    }

    @PostMapping("/disable-route/{serviceId}/{routeId}/{auth}")
    public Mono<ResultInfo<Void>> disableRoute(
            @PathVariable("serviceId") String serviceId,
            @PathVariable("routeId") String routeId,
            @PathVariable("auth") String auth
    ){
        // （内部接口）简单校验一下password
        if (StringUtils.equals(auth, password)){
            routeService.disableRoute(serviceId, routeId);
            return RIUtils.createOK();
        }else{
            return RIUtils.create(ResultCode.FORBIDDEN, "校验不通过", null);
        }
    }
}
