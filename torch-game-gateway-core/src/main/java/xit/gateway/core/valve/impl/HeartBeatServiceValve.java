package xit.gateway.core.valve.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.api.cluster.heartbeatclient.HeartBeatClient;
import xit.gateway.api.valve.ProcessCoreValve;

@Component
public class HeartBeatServiceValve extends ProcessCoreValve {
    private final HeartBeatClient client;

    @Autowired
    public HeartBeatServiceValve(HeartBeatClient client) {
        this.client = client;
    }

    @Override
    protected void work() {
        client.startBeat();
    }
}
