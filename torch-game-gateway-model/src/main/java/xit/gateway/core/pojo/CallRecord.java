package xit.gateway.core.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Table
public class CallRecord {
    @Id
    private String id;
    private String gatewayHost;
    private String gatewayPort;
    private String gatewayUri;
    private String routeId;
    private String routeDesc;
    private String routeHost;
    private int routePort;
    private String routeUrl;
    private LocalDateTime routeCreationDatetime;
    private LocalDateTime routeUpdateDatetime;
    private String serviceId;
    private boolean success;
    private long timestamp; // 调用时间
    private long callTime;  // 调用耗时

    public CallRecord() {
    }

    public CallRecord(String id, String gatewayHost, String gatewayPort, String gatewayUri, String routeId, String routeDesc, String routeHost, int routePort, String routeUrl, LocalDateTime routeCreationDatetime, LocalDateTime routeUpdateDatetime, String serviceId, boolean success, long timestamp, long callTime) {
        this.id = id;
        this.gatewayHost = gatewayHost;
        this.gatewayPort = gatewayPort;
        this.gatewayUri = gatewayUri;
        this.routeId = routeId;
        this.routeDesc = routeDesc;
        this.routeHost = routeHost;
        this.routePort = routePort;
        this.routeUrl = routeUrl;
        this.routeCreationDatetime = routeCreationDatetime;
        this.routeUpdateDatetime = routeUpdateDatetime;
        this.serviceId = serviceId;
        this.success = success;
        this.timestamp = timestamp;
        this.callTime = callTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    public String getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(String gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getGatewayUri() {
        return gatewayUri;
    }

    public void setGatewayUri(String gatewayUri) {
        this.gatewayUri = gatewayUri;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public String getRouteHost() {
        return routeHost;
    }

    public void setRouteHost(String routeHost) {
        this.routeHost = routeHost;
    }

    public int getRoutePort() {
        return routePort;
    }

    public void setRoutePort(int routePort) {
        this.routePort = routePort;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public LocalDateTime getRouteCreationDatetime() {
        return routeCreationDatetime;
    }

    public void setRouteCreationDatetime(LocalDateTime routeCreationDatetime) {
        this.routeCreationDatetime = routeCreationDatetime;
    }

    public LocalDateTime getRouteUpdateDatetime() {
        return routeUpdateDatetime;
    }

    public void setRouteUpdateDatetime(LocalDateTime routeUpdateDatetime) {
        this.routeUpdateDatetime = routeUpdateDatetime;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final CallRecord callRecord;

        public Builder(){
            this.callRecord = new CallRecord();
        }

        public Builder gatewayHost(String host){
            this.callRecord.setGatewayHost(host);
            return this;
        }

        public Builder gatewayPort(String port){
            this.callRecord.setGatewayPort(port);
            return this;
        }

        public Builder gatewayUri(String uri){
            this.callRecord.setGatewayUri(uri);
            return this;
        }

        public Builder route(CalledRoute route){
            this.callRecord.setRouteId(route.getId());
            this.callRecord.setRouteDesc(route.getDesc());
            this.callRecord.setRouteHost(route.getHost());
            this.callRecord.setRoutePort(route.getPort());
            this.callRecord.setRouteUrl(route.getUrl());
            this.callRecord.setRouteCreationDatetime(route.getCreationDatetime());
            this.callRecord.setRouteUpdateDatetime(route.getUpdateDatetime());
            return this;
        }

        public Builder route(Supplier<CalledRoute> supplier){
            return route(supplier.get());
        }

        public Builder serviceId(String serviceId){
            this.callRecord.setServiceId(serviceId);
            return this;
        }

        public Builder success(boolean success){
            this.callRecord.setSuccess(success);
            return this;
        }

        public Builder timestamp(long timestamp){
            this.callRecord.setTimestamp(timestamp);
            return this;
        }

        public Builder callTime(long callTime){
            this.callRecord.setCallTime(callTime);
            return this;
        }

        public CallRecord build(){
            return this.callRecord;
        }
    }

    @Override
    public String toString() {
        return "CallRecord{" +
                "id='" + id + '\'' +
                ", gatewayHost='" + gatewayHost + '\'' +
                ", gatewayPort='" + gatewayPort + '\'' +
                ", gatewayUri='" + gatewayUri + '\'' +
                ", routeId='" + routeId + '\'' +
                ", routeDesc='" + routeDesc + '\'' +
                ", routeHost='" + routeHost + '\'' +
                ", routePort=" + routePort +
                ", routeUrl='" + routeUrl + '\'' +
                ", routeCreationDatetime=" + routeCreationDatetime +
                ", routeUpdateDatetime=" + routeUpdateDatetime +
                ", serviceId='" + serviceId + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                ", callTime=" + callTime +
                '}';
    }
}
