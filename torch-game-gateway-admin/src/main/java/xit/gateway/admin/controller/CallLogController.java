package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xit.gateway.admin.domain.CallLog;
import xit.gateway.admin.service.CallLogService;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/call-log")
public class CallLogController {
    @Resource
    private CallLogService callLogService;

    @GetMapping("list")
    public Mono<ResultInfo<List<CallLog>>> list() {
        List<CallLog> routeList = callLogService.findAll();

        return RIUtils.createOK(routeList);
    }
}
