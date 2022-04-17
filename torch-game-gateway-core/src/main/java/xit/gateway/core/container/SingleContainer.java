package xit.gateway.core.container;

public interface SingleContainer<T> extends Container<T>{
    T get(String key);
}
