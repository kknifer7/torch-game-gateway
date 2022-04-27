package xit.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
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
    protected String name;
    protected String remark;
    protected ProtocolType protocol;
    protected String host;
    protected int port;
    protected String url;
    protected boolean status;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime creationDatetime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateDatetime;

    protected Object extra;

    public Route() {
    }

    public Route(String id, String name, String remark, ProtocolType protocol, String host, int port, String url, boolean status, LocalDateTime creationDatetime, LocalDateTime updateDatetime, Object extra) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = url;
        this.status = status;
        this.creationDatetime = creationDatetime;
        this.updateDatetime = updateDatetime;
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
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
                ", name='" + name + '\'' +
                ", desc='" + remark + '\'' +
                ", protocol=" + protocol +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", url='" + url + '\'' +
                ", disabled=" + status +
                ", creationDatetime=" + creationDatetime +
                ", updateDatetime=" + updateDatetime +
                ", extra=" + extra +
                '}';
    }
}
