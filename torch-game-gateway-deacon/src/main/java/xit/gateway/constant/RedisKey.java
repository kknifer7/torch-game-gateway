package xit.gateway.constant;

public enum RedisKey implements ValueEnum<String>{
    // list
    CALL_RECORD("call_record"),
    // hash
    FUSE_COUNT("fuse_count"),
    // normal
    FUSING_THRESHOLD("fusing_threshold");

    private final String value;

    RedisKey(String value){
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
