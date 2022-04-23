package xit.gateway.deacon.cluster.heartbeatserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.deacon.cluster.heartbeatserver.impl.DefaultNettyHeartBeatServer;

@SpringBootTest
public class TestDefaultNettyHeartBeatServer {
    @Autowired
    private DefaultNettyHeartBeatServer server;

    @Test
    void test(){
        server.start();
    }
}
