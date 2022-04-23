package xit.gateway.utils;

import java.util.UUID;

public class UUIDUtils {
    private UUIDUtils(){}

    public static String getRandom(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
