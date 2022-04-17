package xit.gateway.pojo;

import java.util.function.Supplier;

public class CallRecord {
    private CalledGateway gateway;
    private CalledRoute route;
    private String serviceId;
    private boolean success;
    private long timestamp; // 调用时间
    private long callTime;  // 调用耗时

    public CallRecord() {
    }

    public CallRecord(CalledGateway gateway, CalledRoute route, String serviceId, boolean success, long timestamp, long callTime) {
        this.gateway = gateway;
        this.route = route;
        this.serviceId = serviceId;
        this.success = success;
        this.timestamp = timestamp;
        this.callTime = callTime;
    }

    public CalledGateway getGateway() {
        return gateway;
    }

    public void setGateway(CalledGateway gateway) {
        this.gateway = gateway;
    }

    public CalledRoute getRoute() {
        return route;
    }

    public void setRoute(CalledRoute route) {
        this.route = route;
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
            this.callRecord.setGateway(new CalledGateway());
        }

        public Builder gatewayHost(String host){
            this.callRecord.getGateway().setHost(host);
            return this;
        }

        public Builder gatewayPort(String port){
            this.callRecord.getGateway().setPort(port);;
            return this;
        }

        public Builder gatewayUri(String uri){
            this.callRecord.getGateway().setUri(uri);
            return this;
        }

        public Builder route(CalledRoute route){
            this.callRecord.setRoute(route);
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
                "gateway=" + gateway +
                ", route=" + route +
                ", serviceId='" + serviceId + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                ", callTime=" + callTime +
                '}';
    }
}
