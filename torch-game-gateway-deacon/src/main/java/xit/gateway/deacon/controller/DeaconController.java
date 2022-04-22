package xit.gateway.deacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xit.gateway.deacon.fuse.Fuse;
import xit.gateway.deacon.service.CallRecordService;
import xit.gateway.core.pojo.CallRecord;
import xit.gateway.core.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

@RestController
public class DeaconController {
    private final Fuse fuse;
    private final CallRecordService callRecordService;

    @Autowired
    public DeaconController(Fuse fuse, CallRecordService callRecordService) {
        this.fuse = fuse;
        this.callRecordService = callRecordService;
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
}