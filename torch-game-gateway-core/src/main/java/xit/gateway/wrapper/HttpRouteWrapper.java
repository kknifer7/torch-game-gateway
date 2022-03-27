package xit.gateway.wrapper;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;
import xit.gateway.exception.system.RouteResolvingException;
import xit.gateway.exception.system.SystemException;
import xit.gateway.pojo.HttpRoute;
import xit.gateway.pojo.Route;

import java.util.Map;

/**
 * @author Knifer
 * Description: HTTP路由信息包装
 * Date: 2022/03/25
 */
@SuppressWarnings("rawtypes")
public class HttpRouteWrapper extends Route {

    public enum ReturnDataType{
        MONO,
        FLUX
    }

    private HttpHeaders headers;
    private HttpMethod method;
    private Map<String, Object> params;             // 请求参数
    private Mono body;                              // 请求体
    private Class<?> bodyElementType;               // 请求体类型
    private ReturnDataType returnDataType;          // 返回类型（Mono / Flux）
    private Class<?> returnElementType;             // 返回体类型

    public HttpRouteWrapper() {
    }

    public HttpRouteWrapper(HttpHeaders headers, HttpMethod method, Map<String, Object> params, Mono body, Class<?> bodyElementType, ReturnDataType returnDataType, Class<?> returnElementType) {
        this.headers = headers;
        this.method = method;
        this.params = params;
        this.body = body;
        this.bodyElementType = bodyElementType;
        this.returnDataType = returnDataType;
        this.returnElementType = returnElementType;
    }

    public HttpRouteWrapper(HttpRoute httpRoute){
        BeanUtils.copyProperties(httpRoute, this);

        this.headers = new HttpHeaders(
                CollectionUtils.toMultiValueMap(httpRoute.getHeaders())
        );
        this.method = HttpMethod.resolve(httpRoute.getMethod());
        this.body = Mono.just(httpRoute.getBody());
        try {
            this.bodyElementType = Class.forName(httpRoute.getBodyElementType());
        } catch (ClassNotFoundException e) {
            throw new RouteResolvingException("class not found: " + httpRoute.getBodyElementType());
        }
        try {
            this.returnElementType = Class.forName(httpRoute.getReturnElementType());
        } catch (ClassNotFoundException e) {
            throw new RouteResolvingException("class not found: " + httpRoute.getReturnElementType());
        }
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Mono getBody() {
        return body;
    }

    public void setBody(Mono body) {
        this.body = body;
    }

    public Class<?> getBodyElementType() {
        return bodyElementType;
    }

    public void setBodyElementType(Class<?> bodyElementType) {
        this.bodyElementType = bodyElementType;
    }

    public ReturnDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(ReturnDataType returnDataType) {
        this.returnDataType = returnDataType;
    }

    public Class<?> getReturnElementType() {
        return returnElementType;
    }

    public void setReturnElementType(Class<?> returnElementType) {
        this.returnElementType = returnElementType;
    }

    @Override
    public String toString() {
        return "HttpRouteWrapper{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", url='" + url + '\'' +
                ", disabled=" + disabled +
                ", creationDatetime=" + creationDatetime +
                ", updateDatetime=" + updateDatetime +
                ", extra=" + extra +
                ", headers=" + headers +
                ", method=" + method +
                ", params=" + params +
                ", body=" + body +
                ", bodyElementType=" + bodyElementType +
                ", returnDataType=" + returnDataType +
                ", returnElementType=" + returnElementType +
                '}';
    }
}