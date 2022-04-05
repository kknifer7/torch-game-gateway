package xit.gateway.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper om;

    private JsonUtils(){}

    static {
        om = new ObjectMapper();
    }

    public static<T> T readValue(String filePath, Class<T> clazz) throws IOException {
        return om.readValue(new File(filePath), clazz);
    }

    public static<T> T readValue(String filePath, TypeReference<T> typeReference) throws IOException {
        return om.readValue(new File(filePath), typeReference);
    }

    public static ObjectMapper getOm(){
        return om;
    }
}
