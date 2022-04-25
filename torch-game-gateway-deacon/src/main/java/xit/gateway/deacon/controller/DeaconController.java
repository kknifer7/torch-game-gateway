package xit.gateway.deacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xit.gateway.api.fuse.Fuse;
import xit.gateway.api.service.CallRecordService;
import xit.gateway.constant.ResultCode;
import xit.gateway.deacon.cluster.gateway.container.impl.GlobalGatewayContainer;
import xit.gateway.pojo.CallRecord;
import xit.gateway.pojo.Gateway;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import java.util.Collection;

@RestController
public class DeaconController {
    private final Fuse fuse;
    private final CallRecordService callRecordService;
    private final GlobalGatewayContainer gatewayContainer;

    @Autowired
    public DeaconController(Fuse fuse, CallRecordService callRecordService, GlobalGatewayContainer gatewayContainer) {
        this.fuse = fuse;
        this.callRecordService = callRecordService;
        this.gatewayContainer = gatewayContainer;
    }

    @PutMapping("/record-visit")
    public Mono<ResultInfo<Void>> recordVisit(@RequestBody CallRecord record){
        // 设置调用记录
        callRecordService.add(record);
        // 必要则熔断
        fuse.fuseIfNecessary(record);

        return RIUtils.createOK();
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
}