package xit.gateway.api.container;

import java.io.Serializable;
import java.util.Collection;

public interface SingleContainer<T> extends Container<T>{
    T get(Serializable key);
    T remove(Serializable key);
    Collection<T> getAll();
}
