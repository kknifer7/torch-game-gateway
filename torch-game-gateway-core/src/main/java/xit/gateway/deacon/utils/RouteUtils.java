package xit.gateway.deacon.utils;

import xit.gateway.core.pojo.Route;

public class RouteUtils {
    private RouteUtils(){}

    public static boolean isEnabled(Route route){
        return !route.getDisabled();
    }
}
