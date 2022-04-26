package xit.gateway.utils;

import xit.gateway.pojo.Route;

public class RouteUtils {
    private RouteUtils(){}

    public static boolean isEnabled(Route route){
        return !route.getStatus();
    }
}
