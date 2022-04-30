package xit.gateway.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Table
public class CallLog {
    @Id
    private String id;
    private String gatewayId;
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
    private String serviceName;
    private boolean success;
    private long timestamp; // 调用时间
    private long consumeTime;  // 调用耗时

    public CallLog() {
    }

    public CallLog(String id, String gatewayId, String gatewayHost, String gatewayPort, String gatewayUri, String routeId, String routeDesc, String routeHost, int routePort, String routeUrl, LocalDateTime routeCreationDatetime, LocalDateTime routeUpdateDatetime, String serviceName, boolean success, long timestamp, long consumeTime) {
        this.id = id;
        this.gatewayId = gatewayId;
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
        this.serviceName = serviceName;
        this.success = success;
        this.timestamp = timestamp;
        this.consumeTime = consumeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final CallLog callRecord;

        public Builder(){
            this.callRecord = new CallLog();
        }

        public Builder gatewayId(String id){
            this.callRecord.setGatewayId(id);
            return this;
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
            this.callRecord.setRouteDesc(route.getRemark());
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
            this.callRecord.setServiceName(serviceId);
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
            this.callRecord.setConsumeTime(callTime);
            return this;
        }

        public CallLog build(){
            return this.callRecord;
        }
    }

    @Override
    public String toString() {
        return "CallRecord{" +
                "id='" + id + '\'' +
                ", gatewayId='" + gatewayId + '\'' +
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
                ", serviceId='" + serviceName + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                ", callTime=" + consumeTime +
                '}';
    }
}
