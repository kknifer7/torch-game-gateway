package xit.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xit.gateway.constant.RedisKey;
import xit.gateway.core.fuse.Fuse;
import xit.gateway.pojo.CallRecord;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;
import xit.gateway.utils.RedisUtils;

@RestController
public class DeaconController {
    private final Fuse fuse;

    @Autowired
    public DeaconController(Fuse fuse) {
        this.fuse = fuse;
    }

    @PutMapping("/record-visit")
    public Mono<ResultInfo<Void>> recordVisit(@RequestBody CallRecord record){
        // 设置调用记录
        RedisUtils.lPush(RedisKey.CALL_RECORD, record);
        // 必要则熔断
        //fuse.fuseIfNecessary(record);

        return RIUtils.createOK();
    }

    @PutMapping("/flush-fuse")
    public Mono<ResultInfo<Void>> flushFuse(){
        fuse.flush();

        return RIUtils.createOK();
    }
}