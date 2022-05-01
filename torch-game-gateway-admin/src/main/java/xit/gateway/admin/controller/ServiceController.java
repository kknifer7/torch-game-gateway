package xit.gateway.admin.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xit.gateway.admin.domain.Service;
import xit.gateway.admin.service.ServiceService;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.RIUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/service")
public class ServiceController {
    @Resource
    private ServiceService serviceService;

    @GetMapping("list")
    public Mono<ResultInfo<List<Service>>> list() {
        List<Service> serviceList = serviceService.findAll();
        return RIUtils.createOK(serviceList);
    }

    @GetMapping("info")
    public Mono<ResultInfo<Optional<Service>>> info(@RequestParam("name") String name) {
        Optional<Service> service = serviceService.findByName(name);
        return RIUtils.createOK(service);
    }

    @PostMapping("add")
    public Mono<ResultInfo<Object>> add(@RequestBody Service resources) {
        serviceService.create(resources);
        return RIUtils.createOK();
    }

    @PostMapping("update")
    public Mono<ResultInfo<Object>> update(@RequestBody Service resources) {
        serviceService.update(resources);
        return RIUtils.createOK();
    }

    @PostMapping("delete")
    public Mono<ResultInfo<Object>> delete(@RequestBody Map<String, Long> dto) {
        serviceService.delete(dto.get("id"));
        return RIUtils.createOK();
    }
}
