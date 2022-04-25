package xit.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import xit.gateway.api.loadbalancer.Loadbalanceable;

import java.time.LocalDateTime;

public class Gateway implements Loadbalanceable {
    private String id;
    private String name;
    private String host;
    private int port;
    private boolean useSSL;
    private boolean backup;
    private boolean disabled;
    private int cpuCores;
    private double cpuSys;
    private double totalMemory;
    private double freeMemory;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public Gateway() {
    }

    public Gateway(String id, String name, String host, int port, boolean useSSL, boolean backup, boolean disabled, int cpuCores, double cpuSys, double totalMemory, double freeMemory, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
        this.useSSL = useSSL;
        this.backup = backup;
        this.disabled = disabled;
        this.cpuCores = cpuCores;
        this.cpuSys = cpuSys;
        this.totalMemory = totalMemory;
        this.freeMemory = freeMemory;
        this.createAt = createAt;
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

    public boolean getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public boolean getBackup() {
        return backup;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public double getCpuSys() {
        return cpuSys;
    }

    public void setCpuSys(double cpuSys) {
        this.cpuSys = cpuSys;
    }

    public double getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(double totalMemory) {
        this.totalMemory = totalMemory;
    }

    public double getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(double freeMemory) {
        this.freeMemory = freeMemory;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Gateway{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", useSSL=" + useSSL +
                ", backup=" + backup +
                ", disabled=" + disabled +
                ", createAt=" + createAt +
                '}';
    }
}
