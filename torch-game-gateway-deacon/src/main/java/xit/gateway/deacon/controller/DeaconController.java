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
import java.util.Map;

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
        // ??????????????????
        callRecordService.add(record);
        // ???????????????
        fuse.fuseIfNecessary(record);

        return RIUtils.createOK();
    }

    private void validateIntervalCall(String password){
        if (!StringUtils.equals(password, deaconPassword)){
            throw new AccessForbiddenException("?????????????????????????????????");
        }
    }

    @PutMapping("/flush-fuse")
    public Mono<ResultInfo<Void>> flushFuse(){
        fuse.flush();

        return RIUtils.createOK();
    }

    @PutMapping("/flush-backup")
    public Mono<ResultInfo<Void>> flushBackup(){
        gatewaySelector.refresh();

        return RIUtils.createOK();
    }

    @PostMapping("/list-gateways")
    public Mono<ResultInfo<Collection<Gateway>>> listGateways(){
        return RIUtils.create(ResultCode.OK, null, gatewayContainer.getAll());
    }

    @PostMapping("/get-gateway-num")
    public Mono<ResultInfo<Map<String, Integer>>> getGatewayNum(){
        return RIUtils.createOK(Map.of("num", gatewayContainer.getAll().size()));
    }

    @RequestMapping("/service/{serviceId}/**")
    public Mono<?> all(@PathVariable("serviceId") String serviceId, ServerWebExchange exchange){
        Gateway gateway = (Gateway) loadbalancer.choose(gatewaySelector.select(), requestContext);

        if (gateway == null){
            return RIUtils.create(ResultCode.REQUESTER_NOT_FOUND, "??????????????????", null);
        }
        logger.info("????????????: {}", gateway.getName());

        return gatewayAgent.proxy(serviceId, gateway, exchange);
    }
}