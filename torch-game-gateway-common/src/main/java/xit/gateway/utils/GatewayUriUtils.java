package xit.gateway.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Knifer
 * Date: 2022/04/29
 * Description: 用于处理网关uri的工具类
 */
public class GatewayUriUtils {
    private GatewayUriUtils(){}

    private static final String LOGIN_URI = "/login";
    private static final String SERVICE_URI = "/service";
    private static final String SERVICE_URI_WITH_NEXT_SEPARATOR = "/service/";

    public static boolean matchLogin(String path){
        return StringUtils.equals(path, LOGIN_URI);
    }


    // 是否调用的是服务代理接口
    public static boolean isUsingService(String path){
        return path.contains(SERVICE_URI);
    }

    // 从服务代理uri中取得服务名
    public static String getServiceNameFromUri(String path){
        String partWithServiceName = path.split(SERVICE_URI_WITH_NEXT_SEPARATOR, 2)[1];

        for (int i = 0; i < partWithServiceName.length(); i++){
            if (partWithServiceName.charAt(i) == '/'){
                return partWithServiceName.substring(0, i);
            }
        }

        return partWithServiceName;
    }
}
