package xit.gateway.core.container;

import java.util.List;

public interface MultiContainer<T> extends Container<T>{
    List<T> get(String key);
}
