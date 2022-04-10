package xit.gateway.wrapper;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import xit.gateway.pojo.Route;

import java.util.List;

/**
 * TODO 此类弃用，在后续提交中删除
 * @author Knifer
 * Description: HTTP路由信息包装
 * Date: 2022/03/25
 */
@SuppressWarnings("rawtypes")
public class HttpRequestWrapper extends Route {

    public enum ReturnDataType{
        MONO,
        FLUX
    }

    private HttpHeaders headers;
    private HttpMethod method;
    private List<MediaType> accept;
    private MultiValueMap cookies;
    private RequestPath path;
    private MultiValueMap<String, String> queryParams;      // 请求参数
    private Flux<DataBuffer> body;                          // 请求体
    private Class<?> bodyElementType;                       // 请求体类型
    private ReturnDataType returnDataType;                  // 返回类型（Mono / Flux）
    private Class<?> returnElementType;                     // 返回体类型

    public HttpRequestWrapper() {
    }

    public HttpRequestWrapper(HttpHeaders headers, HttpMethod method, List<MediaType> accept, MultiValueMap<String, String> queryParams, Flux<DataBuffer> body, Class<?> bodyElementType, ReturnDataType returnDataType, Class<?> returnElementType) {
        this.headers = headers;
        this.method = method;
        this.accept = accept;
        this.queryParams = queryParams;
        this.body = body;
        this.bodyElementType = bodyElementType;
        this.returnDataType = returnDataType;
        this.returnElementType = returnElementType;
    }

    public HttpRequestWrapper(ServerHttpRequest request){
        this.headers = request.getHeaders();
        this.accept = request.getHeaders().getAccept();
        this.queryParams = request.getQueryParams();
        this.method = request.getMethod();
        this.body = request.getBody();
        this.cookies = request.getCookies();
        this.path = request.getPath();
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

    public List<MediaType> getAccept() {
        return accept;
    }

    public void setAccept(List<MediaType> accept) {
        this.accept = accept;
    }

    public MultiValueMap<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(MultiValueMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Flux<DataBuffer> getBody() {
        return body;
    }

    public void setBody(Flux<DataBuffer> body) {
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
                ", queryParams=" + queryParams +
                ", body=" + body +
                ", bodyElementType=" + bodyElementType +
                ", returnDataType=" + returnDataType +
                ", returnElementType=" + returnElementType +
                '}';
    }
}