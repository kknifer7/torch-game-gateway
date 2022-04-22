package xit.gateway.core.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import xit.gateway.core.constant.ProtocolType;

import java.time.LocalDateTime;

/**
 * @author Knifer
 * Description: 路由信息类
 * Date: 2022/03/25
 */
public class Route {
    protected String id;
    protected String name;
    protected String desc;
    protected ProtocolType protocol;
    protected String host;
    protected int port;
    protected String url;
    protected boolean disabled;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime creationDatetime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateDatetime;

    protected Object extra;

    public Route() {
    }

    public Route(String id, String name, String desc, ProtocolType protocol, String host, int port, String url, boolean disabled, LocalDateTime creationDatetime, LocalDateTime updateDatetime, Object extra) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = url;
        this.disabled = disabled;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
                ", desc='" + desc + '\'' +
                ", protocol=" + protocol +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", url='" + url + '\'' +
                ", disabled=" + disabled +
                ", creationDatetime=" + creationDatetime +
                ", updateDatetime=" + updateDatetime +
                ", extra=" + extra +
                '}';
    }
}
