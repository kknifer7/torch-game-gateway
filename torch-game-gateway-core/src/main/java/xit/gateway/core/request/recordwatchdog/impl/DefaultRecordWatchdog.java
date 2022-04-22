package xit.gateway.core.request.recordwatchdog.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import xit.gateway.core.request.recordwatchdog.RecordWatchdog;
import xit.gateway.core.pojo.RequesterProxyResult;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class DefaultRecordWatchdog implements RecordWatchdog {
    private final Executor executor;
    private final WebClient webClient;

    public DefaultRecordWatchdog(
            @Value("${torch.gateway.call-trace.record-threads}")
            Integer threads,
            @Value("${torch.gateway.call-trace.deacon.address}")
            String deaconAddress
    ){
        this.executor = Executors.newFixedThreadPool(threads);
        this.webClient = WebClient.create(deaconAddress);
    }

    @Override
    public void watchAndSend(RequesterProxyResult proxyResult) {
        executor.execute(() -> {
            // TODO 如有时间，优化这里的循环
            while (true){
                if (proxyResult.isCompleted()){
                    webClient.put()
                            .bodyValue(proxyResult.getCallRecord())
                            .retrieve()
                            .bodyToMono(String.class)
                            .subscribe();
                    return;
                }
            }
        });
    }
}
