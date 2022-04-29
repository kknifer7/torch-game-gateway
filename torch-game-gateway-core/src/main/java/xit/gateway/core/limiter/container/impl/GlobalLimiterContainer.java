package xit.gateway.core.limiter.container.impl;

import org.springframework.stereotype.Component;
import xit.gateway.api.route.limiter.Limiter;
import xit.gateway.api.route.limiter.container.LimiterContainer;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalLimiterContainer implements LimiterContainer {
    private final Map<Serializable, Limiter> map = new ConcurrentHashMap<>();

    @Override
    public void put(Limiter limiter) {
        map.put(limiter.getId(), limiter);
    }

    @Override
    public void putAll(List<Limiter> limiters) {
        limiters.forEach(this::put);
    }

    @Override
    public boolean contains(Serializable key) {
        return map.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Limiter get(Serializable key) {
        return map.get(key);
    }

    @Override
    public Limiter remove(Serializable key) {
        return map.remove(key);
    }

    @Override
    public Collection<Limiter> getAll() {
        return map.values();
    }
}
