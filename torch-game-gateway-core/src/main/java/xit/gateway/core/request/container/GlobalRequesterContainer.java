package xit.gateway.core.request.container;

import org.springframework.stereotype.Component;
import xit.gateway.core.container.SingleContainer;
import xit.gateway.core.request.requester.Requester;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalRequesterContainer implements SingleContainer<Requester> {
    protected final Map<String, Requester> requesterMap;

    public GlobalRequesterContainer() {
        this.requesterMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(Requester requester) {
        requester.getKeys().forEach(each -> requesterMap.put(each, requester));
    }

    @Override
    public void putAll(List<Requester> requesters) {
        for (Requester requester : requesters){
            put(requester);
        }
    }

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
