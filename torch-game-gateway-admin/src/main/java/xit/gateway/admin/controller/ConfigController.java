package xit.gateway.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.admin.domain.Config;
import xit.gateway.admin.service.ConfigService;
import xit.gateway.admin.service.HTTPService;
import xit.gateway.constant.RedisChannel;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;
import xit.gateway.utils.RedisUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @Autowired
    private HTTPService httpService;

    @GetMapping("list")
    public Mono<ResultInfo<List<Config>>> list() {
        List<Config> routeList = configService.findAll();
        return RIUtils.createOK(routeList);
    }


    @PostMapping("add")
    public Mono<ResultInfo<Object>> add(@RequestBody Config resources) {
        configService.create(resources);
        return RIUtils.createOK();
    }

    @PostMapping("update")
    public Mono<ResultInfo<Object>> update(@RequestBody Config resources, @RequestHeader HttpHeaders headers) {
        configService.update(resources);

        String key = resources.getKee();

        switch (key) {
            case "enable_fusing_on_limiting":
            case "fusing_threshold":
            case "fusing_timeout":
            case "fusing_timeunit":
                httpService.put("/flush-fuse", headers, new HashMap<>());
                break;
            case "backup_enable_threshold":
                httpService.put("/flush-backup", headers, new HashMap<>());
                break;
            case "limiting_threshold":
            case "limiting_timeout":
            case "limiting_time_unit":
                RedisUtils.publish(RedisChannel.LIMITER_SETTINGS_FLUSH, 1);
                break;
        }
        return RIUtils.createOK();
    }

    @PostMapping("delete")
    public Mono<ResultInfo<Object>> delete(@RequestBody Map<String, Long> dto) {
        configService.delete(dto.get("id"));
        return RIUtils.createOK();
    }
}
