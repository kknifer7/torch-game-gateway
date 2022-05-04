package xit.gateway.constant;

public enum RedisChannel implements ValueEnum<String>{
    // Route
    ROUTE("ROUTE"),
    // List<Route>
    ROUTE_LIST("ROUTE_LIST"),
    // routeId
    ROUTE_DELETE("ROUTE_DELETE"),
    // serviceName
    ROUTE_LIST_DELETE("ROUTE_LIST_DELETE"),
    // limiter
    LIMITER("LIMITER"),
    // 仅作通知用，推送即生效
    LIMITER_SETTINGS_FLUSH("LIMITER_SETTINGS_FLUSH"),
    // 仅作通知用，推送即生效
    ENABLE_FUSING_ON_LIMITING("ENABLE_FUSING_ON_LIMITING");

    private final String value;

    RedisChannel(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
