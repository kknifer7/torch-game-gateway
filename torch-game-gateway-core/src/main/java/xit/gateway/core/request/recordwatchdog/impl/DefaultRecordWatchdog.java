package xit.gateway.core.request.recordwatchdog.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import xit.gateway.api.context.GatewayContext;
import xit.gateway.api.request.recordwatchdog.RecordWatchdog;
import xit.gateway.pojo.CallRecord;
import xit.gateway.pojo.RequesterProxyResult;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class DefaultRecordWatchdog implements RecordWatchdog {
    private final Executor executor;
    private final String gatewayId;
    private final WebClient webClient;

    @Autowired
    public DefaultRecordWatchdog(
            GatewayContext gatewayContext,
            @Value("${torch.gateway.call-trace.record-threads}")
            Integer threads,
            @Value("${torch.gateway.call-trace.deacon.address}")
            String deaconAddress,
            @Value("${torch.gateway.call-trace.deacon.password}")
            String password
    ){
        this.executor = Executors.newFixedThreadPool(threads);
        this.gatewayId = gatewayContext.gateway().getId();
        this.webClient = WebClient.create(concatDeaconAddressAndPassword(deaconAddress, password));
    }

    private String concatDeaconAddressAndPassword(String deaconAddress, String password){
        if (deaconAddress.charAt(deaconAddress.length() - 1) == '/'){
            deaconAddress += password;
        }else{
            deaconAddress += ("/" + password);
        }

        return deaconAddress;
    }

    @Override
    public void watchAndSend(RequesterProxyResult proxyResult) {
        executor.execute(() -> {
            CallRecord callRecord;

            while (true){
                if (proxyResult.isCompleted()){
                    callRecord = proxyResult.getCallRecord();
                    callRecord.setGatewayId(gatewayId); // 一点也不优雅，有时间考虑开发一个holder，让requester在请求前直接设置gatewayId
                    webClient.put()
                            .bodyValue(callRecord)
                            .retrieve()
                            .bodyToMono(String.class)
                            .subscribe();
                    return;
                }
            }
        });
    }
}
