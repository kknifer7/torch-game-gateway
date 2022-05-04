package xit.gateway.core.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.api.route.limiter.manager.LimiterManager;
import xit.gateway.api.service.RouteService;
import xit.gateway.constant.ResultCode;
import xit.gateway.core.dto.AddLimiterForUserDTO;
import xit.gateway.pojo.Limiter;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RIUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Knifer
 * Description: 负责处理当前实例的变更任务
 * Date: 2022/04/13
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    private final RouteService routeService;
    private final RoutesContainer routesContainer;
    private final LimiterManager limiterManager;
    private final String password;

    @Autowired
    public ActionController(
            RouteService routeService,
            RoutesContainer routesContainer,
            LimiterManager limiterManager,
            @Value("${torch.gateway.password}") String password
    ) {
        this.routeService = routeService;
        this.routesContainer = routesContainer;
        this.password = password;
        this.limiterManager = limiterManager;
    }

    @PostMapping("/admin/add-service/{serviceId}")
    public Mono<ResultInfo<Void>> addService(@PathVariable("serviceId") String serviceId){
        routeService.addRoutesFromRedis(serviceId);

        return RIUtils.createOK();
    }


    @PostMapping("/admin/add-route/{routeId}")
    public Mono<ResultInfo<Void>> addRoute(
            @PathVariable("routeId") String routeId
    ){
        routeService.addRouteFromRedis(routeId);

        return RIUtils.createOK();
    }

    @PostMapping("/admin/update-route/{routeId}")
    public Mono<ResultInfo<Void>> updateRoute(
            @PathVariable("routeId") String routeId
    ){
        routeService.updateRouteFromRedis(routeId);

        return RIUtils.createOK();
    }

    @PostMapping("/admin/list-routes")
    public Mono<ResultInfo<List<Route>>> listRoutes(){
        return RIUtils.createOK(routesContainer.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/admin/add-limiter-for-user")
    public Mono<ResultInfo<Void>> addLimiterForUser(@RequestBody AddLimiterForUserDTO dto){
        limiterManager.addLimiter(
                dto.getUserId(),
                dto.getLimit(),
                dto.getLimitingTimeout(),
                dto.getLimitingTimeUnit()
        );

        return RIUtils.createOK();
    }

    @PostMapping("/admin/remove-limiter-for-user/{id}")
    public Mono<ResultInfo<Void>> removeLimiterForUser(@PathVariable("id") Long id){
        limiterManager.removeLimiter(id);

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

    @PostMapping("/admin/list-limiters")
    public Mono<ResultInfo<List<Limiter>>> listLimiters(){
        return RIUtils.createOK(limiterManager.getAllLimiterVO());
    }
}
