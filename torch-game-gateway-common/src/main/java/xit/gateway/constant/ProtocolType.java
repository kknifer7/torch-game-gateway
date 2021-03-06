package xit.gateway.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProtocolType implements ValueEnum<String>{
    HTTP("http"),
    HTTPS("https"),
    RPC("rpc");

    @JsonValue
    private final String value;

    ProtocolType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
