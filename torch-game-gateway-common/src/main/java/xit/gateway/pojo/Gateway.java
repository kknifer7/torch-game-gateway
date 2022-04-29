package xit.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import xit.gateway.api.loadbalancer.Loadbalanceable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gateway implements Loadbalanceable {
    private String id;
    private String name;
    private String host;
    private int port;
    private boolean useSSL;
    private boolean backup;
    private boolean status;
    private int cpuCores;
    private double cpuSys;
    private long totalMemory;
    private long freeMemory;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public boolean getUseSSL() {
        return useSSL;
    }

    public boolean getBackup() {
        return backup;
    }

    public boolean getStatus(){
        return status;
    }
}
