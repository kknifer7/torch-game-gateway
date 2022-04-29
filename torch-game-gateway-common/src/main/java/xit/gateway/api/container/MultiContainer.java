package xit.gateway.api.container;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MultiContainer<T> extends Container<T>{
    List<T> get(Serializable key);
    List<T> remove(Serializable key);
    Set<Map.Entry<String, List<T>>> entrySet();
}
