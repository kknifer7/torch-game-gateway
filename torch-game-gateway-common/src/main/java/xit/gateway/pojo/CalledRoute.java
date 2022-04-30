package xit.gateway.pojo;

import java.time.LocalDateTime;

/**
 * @author Knifer
 * Description: 被访问的路由实体（用于记录用户访问行为）。
 * Date: 2022/04/17
 */
public class CalledRoute {
    protected String id;
    protected String remark;
    protected String host;
    protected int port;
    protected String url;
    protected LocalDateTime creationDatetime;
    protected LocalDateTime updateDatetime;

    public CalledRoute() {
    }

    public CalledRoute(String id, String remark, String host, int port, String url, LocalDateTime creationDatetime, LocalDateTime updateDatetime) {
        this.id = id;
        this.remark = remark;
        this.host = host;
        this.port = port;
        this.url = url;
        this.creationDatetime = creationDatetime;
        this.updateDatetime = updateDatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "CalledRoute{" +
                "id='" + id + '\'' +
                ", desc='" + remark + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", url='" + url + '\'' +
                ", creationDatetime=" + creationDatetime +
                ", updateDatetime=" + updateDatetime +
                '}';
    }
}
