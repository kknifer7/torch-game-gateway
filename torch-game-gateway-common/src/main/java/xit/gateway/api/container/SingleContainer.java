package xit.gateway.api.container;

public interface SingleContainer<T> extends Container<T>{
    T get(String key);
}
