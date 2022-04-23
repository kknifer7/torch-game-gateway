package xit.gateway.pojo;

import java.util.List;

/**
 * @author Knifer
 * Description: 站点
 * Date: 2022/04/22
 */
public class Site {
    private String id;
    private String name;
    private String baseUrl;
    private List<Route> routeList;

    public Site() {
    }

    public Site(String id, String name, String baseUrl, List<Route> routeList) {
        this.id = id;
        this.name = name;
        this.baseUrl = baseUrl;
        this.routeList = routeList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
