package xit.gateway.constant;

public enum RedisChannel implements ValueEnum<String>{
    // Route
    ROUTE("route"),
    // List<Route>
    ROUTE_LIST("route_list"),
    // routeId
    ROUTE_DELETE("route_delete"),
    // serviceName
    ROUTE_LIST_DELETE("route_list_delete");

    private final String value;

    RedisChannel(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
