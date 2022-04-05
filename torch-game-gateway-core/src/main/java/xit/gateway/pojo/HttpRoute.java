package xit.gateway.pojo;

import xit.gateway.wrapper.HttpRequestWrapper;

import java.util.List;
import java.util.Map;

/**
 * TODO 目前此类无用，待重新设计
 * @author Knifer
 * Description: HTTP路由信息
 * Date: 2022/03/25
 */
public class HttpRoute extends Route {
    private Map<String, List<String>> headers;
    private String method;
    private String accept;
    private Map<String, Object> params;                                 // 请求参数
    private String body;                                                // 请求体
    private String bodyElementType;                                     // 请求体类型
    private HttpRequestWrapper.ReturnDataType returnDataType;             // 返回类型
    private String returnElementType;                                   // 返回体类型

    public HttpRoute() {
    }

    public HttpRoute(Map<String, List<String>> headers, String method, String accept, Map<String, Object> params, String body, String bodyElementType, HttpRequestWrapper.ReturnDataType returnDataType, String returnElementType) {
        this.headers = headers;
        this.method = method;
        this.accept = accept;
        this.params = params;
        this.body = body;
        this.bodyElementType = bodyElementType;
        this.returnDataType = returnDataType;
        this.returnElementType = returnElementType;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyElementType() {
        return bodyElementType;
    }

    public void setBodyElementType(String bodyElementType) {
        this.bodyElementType = bodyElementType;
    }

    public HttpRequestWrapper.ReturnDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(HttpRequestWrapper.ReturnDataType returnDataType) {
        this.returnDataType = returnDataType;
    }

    public String getReturnElementType() {
        return returnElementType;
    }

    @Override
    public String toString() {
        return "HttpRoute{" +
                "headers=" + headers +
                ", method='" + method + '\'' +
                ", params=" + params +
                ", body='" + body + '\'' +
                ", bodyElementType='" + bodyElementType + '\'' +
                ", returnDataType=" + returnDataType +
                ", returnElementType='" + returnElementType + '\'' +
                '}';
    }

    public void setReturnElementType(String returnElementType) {
        this.returnElementType = returnElementType;
    }
}
