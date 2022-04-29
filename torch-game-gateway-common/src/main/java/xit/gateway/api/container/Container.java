package xit.gateway.api.container;

import java.io.Serializable;
import java.util.List;

public interface Container<T>{
    void put(T obj);
    void putAll(List<T> obj);
    boolean contains(Serializable key);
    boolean isEmpty();
    default void handleDuplicate(T obj){}
}
