package xit.gateway.deacon.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.api.cluster.gateway.selector.GatewaySelector;
import xit.gateway.api.fuse.Fuse;
import xit.gateway.api.cluster.gateway.agent.GatewayAgent;
import xit.gateway.api.request.context.RequestContext;
import xit.gateway.api.route.loadbalancer.Loadbalancer;
import xit.gateway.api.service.CallLogService;
import xit.gateway.constant.ResultCode;
import xit.gateway.deacon.cluster.gateway.container.impl.GlobalGatewayContainer;
import xit.gateway.exception.user.AccessForbiddenException;
import xit.gateway.pojo.CallLog;
import xit.gateway.pojo.Gateway;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.request.context.impl.DefaultRequestContext;
import xit.gateway.utils.RIUtils;

import java.util.Collection;

@RestController
public class DeaconController {
    private final Fuse fuse;
    private final CallLogService callRecordService;
    private final GatewaySelector gatewaySelector;
    private final GlobalGatewayContainer gatewayContainer;
    private final Loadbalancer loadbalancer;
    private final RequestContext requestContext;
    private final GatewayAgent gatewayAgent;
    private final String deaconPassword;
    private final Logger logger = LoggerFactory.getLogger(DeaconController.class);

    @Autowired
    public DeaconController(
            Fuse fuse,
            CallLogService callRecordService,
            GatewaySelector gatewaySelector,
            GlobalGatewayContainer gatewayContainer,
            Loadbalancer loadbalancer,
            GatewayAgent gatewayAgent,
            @Value("${torch.gateway.deacon.password}") String deaconPassword
    ) {
        this.fuse = fuse;
        this.callRecordService = callRecordService;
        this.gatewayContainer = gatewayContainer;
        this.gatewaySelector = gatewaySelector;
        this.loadbalancer = loadbalancer;
        this.requestContext = new DefaultRequestContext();
        this.gatewayAgent = gatewayAgent;
        this.deaconPassword = deaconPassword;
    }

    @PutMapping("/internal/record-visit/{password}")
    public Mono<ResultInfo<Void>> recordVisit(@RequestBody CallLog record, @PathVariable("password") String password){
        validateIntervalCall(password);
        // 设置调用记录
        callRecordService.add(record);
        // 必要则熔断
        fuse.fuseIfNecessary(record);

        return RIUtils.createOK();
    }

    private void validateIntervalCall(String password){
        if (!StringUtils.equals(password, deaconPassword)){
            throw new AccessForbiddenException("内部调用失败，口令错误");
        }
    }

    @PutMapping("/flush-fuse")
    public Mono<ResultInfo<Void>> flushFuse(){
        fuse.flush();

        return RIUtils.createOK();
    }

    @PostMapping("/list-gateways")
    public Mono<ResultInfo<Collection<Gateway>>> listGateways(){
        return RIUtils.create(ResultCode.OK, null, gatewayContainer.getAll());
    }

    @RequestMapping("/service/{serviceId}/**")
    public Mono<?> all(@PathVariable("serviceId") String serviceId, ServerWebExchange exchange){
        Gateway gateway = (Gateway) loadbalancer.choose(gatewaySelector.select(), requestContext);

        if (gateway == null){
            return RIUtils.create(ResultCode.REQUESTER_NOT_FOUND, "暂无可用网关", null);
        }
        logger.info("网关调用: {}", gateway.getName());

        return gatewayAgent.proxy(serviceId, gateway, exchange);
    }
}