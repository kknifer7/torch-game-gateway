package xit.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Gateway {
    private String id;
    private String name;
    private String host;
    private int port;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public Gateway() {
    }

    public Gateway(String id, String name, String host, int port) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
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
}
