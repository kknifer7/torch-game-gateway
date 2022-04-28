package xit.gateway.constant;

public enum ResultCode implements ValueEnum<Integer>{
    OK(200),
    BAD_REQUEST(400),
    ROUTE_DISABLED(401),
    FORBIDDEN(403),
    VERIFICATION_FAILED(501),
    REQUEST_FAILED(500),
    REQUESTER_NOT_FOUND(404);

    private final Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
