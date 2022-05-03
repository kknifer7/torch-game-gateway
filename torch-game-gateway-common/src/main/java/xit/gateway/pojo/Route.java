package xit.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import xit.gateway.api.loadbalancer.Loadbalanceable;
import xit.gateway.constant.ProtocolType;

import java.time.LocalDateTime;

/**
 * @author Knifer
 * Description: 路由信息类
 * Date: 2022/03/25
 */
public class Route implements Loadbalanceable {
    protected String id;
    protected String serviceName;
    protected String remark;
    protected ProtocolType protocol;
    protected String host;
    protected Integer port;
    protected String url;
    protected Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;

    protected Object extra;

    public Route() {
    }

    public Route(String id, String serviceName, String remark, ProtocolType protocol, String host, Integer port, String url, Boolean status, LocalDateTime createTime, LocalDateTime updateTime, Object extra) {
        this.id = id;
        this.serviceName = serviceName;
        this.remark = remark;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = url;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", name='" + serviceName + '\'' +
                ", desc='" + remark + '\'' +
                ", protocol=" + protocol +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", url='" + url + '\'' +
                ", disabled=" + status +
                ", creationDatetime=" + createTime +
                ", updateDatetime=" + updateTime +
                ", extra=" + extra +
                '}';
    }
}
