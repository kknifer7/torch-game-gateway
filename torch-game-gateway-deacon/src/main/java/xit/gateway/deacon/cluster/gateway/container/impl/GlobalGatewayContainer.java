package xit.gateway.deacon.cluster.gateway.container.impl;

import org.springframework.stereotype.Component;
import xit.gateway.api.container.SingleContainer;
import xit.gateway.pojo.Gateway;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalGatewayContainer implements SingleContainer<Gateway> {
    private final Map<String, Gateway> map;

    public GlobalGatewayContainer() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void put(Gateway gateway) {
        map.put(gateway.getId(), gateway);
    }

    @Override
    public void putAll(List<Gateway> gateways) {
        gateways.forEach(this::put);
    }

    @Override
    public boolean contains(String key) {
        return map.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Gateway get(String key) {
        return map.get(key);
    }

    @Override
    public Gateway remove(String key) {
        return map.remove(key);
    }
}
