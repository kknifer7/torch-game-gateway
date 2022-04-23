package xit.gateway.pojo;

import reactor.core.publisher.Mono;

/**
 * @author Knifer
 * Description: 请求器返回的结果（包括调用记录和调用结果）
 * Date: 2022/04/17
 */
public class RequesterProxyResult {
    private CallRecord callRecord;
    private Mono<?> result;
    private volatile boolean completed;

    public RequesterProxyResult() {
    }

    public RequesterProxyResult(CallRecord callRecord, Mono<?> result) {
        this.callRecord = callRecord;
        this.result = result;
        this.completed = false;
    }

    public CallRecord getCallRecord() {
        return callRecord;
    }

    public void setCallRecord(CallRecord callRecord) {
        this.callRecord = callRecord;
    }

    public Mono<?> getResult() {
        return result;
    }

    public void setResult(Mono<?> result) {
        this.result = result;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
