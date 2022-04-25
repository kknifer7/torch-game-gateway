package xit.gateway.api.container;

import java.util.Collection;

public interface SingleContainer<T> extends Container<T>{
    T get(String key);
    T remove(String key);
    Collection<T> getAll();
}
