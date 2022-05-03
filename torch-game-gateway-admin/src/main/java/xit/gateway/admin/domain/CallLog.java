package xit.gateway.admin.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "call_log")
public class CallLog {
    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "my_id", strategy = "xit.gateway.admin.utils.MyIdGenerator")
    @GeneratedValue(generator = "my_id")
    private String id;

    @Column
    private String gatewayId;
    @Column
    private String gatewayHost;
    @Column
    private String gatewayPort;
    @Column
    private String gatewayUri;
    @Column
    private String routeId;
    @Column
    private String routeDesc;
    @Column
    private String routeHost;
    @Column
    private int routePort;
    @Column
    private String routeUrl;
    @Column
    private LocalDateTime routeCreationDatetime;
    @Column
    private LocalDateTime routeUpdateDatetime;
    @Column
    private String serviceName;
    @Column
    private boolean success;
    @Column
    private long startTimestamp;// 调用时间
    @Column
    private long consumeTime; // 调用耗时

}