package xit.gateway.api.container;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MultiContainer<T> extends Container<T>{
    List<T> get(String key);
    Set<Map.Entry<String, List<T>>> entrySet();
}
