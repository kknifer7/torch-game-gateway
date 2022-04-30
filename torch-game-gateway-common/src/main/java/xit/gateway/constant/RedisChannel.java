package xit.gateway.constant;

public enum RedisChannel implements ValueEnum<String>{
    // Route
    ROUTE("ROUTE"),
    // List<Route>
    ROUTE_LIST("ROUTE_LIST"),
    // routeId
    ROUTE_DELETE("ROUTE_DELETE"),
    // serviceName
    ROUTE_LIST_DELETE("ROUTE_LIST_DELETE");

    private final String value;

    RedisChannel(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
