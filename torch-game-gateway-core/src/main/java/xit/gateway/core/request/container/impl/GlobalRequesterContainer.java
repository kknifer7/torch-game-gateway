package xit.gateway.core.request.container.impl;

import org.springframework.stereotype.Component;
import xit.gateway.core.request.container.RequesterContainer;
import xit.gateway.core.request.requester.Requester;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalRequesterContainer implements RequesterContainer {
    protected final Map<String, Requester> requesterMap;

    public GlobalRequesterContainer() {
        this.requesterMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(Requester requester) {
        requester.getKeys().forEach(each -> requesterMap.put(each, requester));
    }

    @Override
    public void putAll(Requester[] requesters) {
        for (Requester requester : requesters){
            put(requester);
        }
    }

    @Override
    public Requester get(String primary) {
        return requesterMap.get(primary);
    }

    @Override
    public boolean contains(String primaryKey) {
        return requesterMap.containsKey(primaryKey);
    }

    @Override
    public boolean isEmpty() {
        return requesterMap.isEmpty();
    }
}
