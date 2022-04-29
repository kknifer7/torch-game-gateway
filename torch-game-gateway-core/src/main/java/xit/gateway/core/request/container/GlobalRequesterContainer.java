package xit.gateway.core.request.container;

import org.springframework.stereotype.Component;
import xit.gateway.api.request.container.RequesterContainer;
import xit.gateway.api.request.requester.Requester;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
        requesterMap.put(requester.getKeyInContainer(), requester);
    }

    @Override
    public void putAll(List<Requester> requesters) {
        for (Requester requester : requesters){
            put(requester);
        }
    }

    @Override
    public Requester get(Serializable primary) {
        return requesterMap.get(primary);
    }

    @Override
    public Requester remove(Serializable key) {
        return requesterMap.remove(key);
    }

    @Override
    public Collection<Requester> getAll() {
        return requesterMap.values();
    }

    @Override
    public boolean contains(Serializable primaryKey) {
        return requesterMap.containsKey(primaryKey);
    }

    @Override
    public boolean isEmpty() {
        return requesterMap.isEmpty();
    }
}
