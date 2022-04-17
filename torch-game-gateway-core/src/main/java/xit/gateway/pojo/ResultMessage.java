package xit.gateway.pojo;

import xit.gateway.constant.ValueEnum;

/**
 * @author Knifer
 * Description: 路由返回信息（一般是调用错误产生的）
 * Date: 2022/04/05
 */
public class ResultMessage {
    private Integer code;
    private String msg;
    private String path;
    private Long timestamp;

    public ResultMessage() {
    }

    public ResultMessage(Integer code, String msg, String path, Long timestamp) {
        this.code = code;
        this.msg = msg;
        this.path = path;
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public enum ResultCode implements ValueEnum<Integer> {
        OK(200),
        ROUTE_DISABLED(401),
        REQUEST_FAILED(500),
        REQUESTER_NOT_FOUND(404);

        private final Integer code;

        ResultCode(Integer code) {
            this.code = code;
        }

        @Override
        public Integer getValue() {
            return code;
        }
    }
}
