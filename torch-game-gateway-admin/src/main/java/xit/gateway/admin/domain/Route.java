package xit.gateway.admin.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import xit.gateway.constant.ProtocolType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "my_id", strategy = "xit.gateway.admin.utils.MyIdGenerator")
    @GeneratedValue(generator = "my_id")
    private String id;

    @Column()
    private String serviceName;

    @Column()
    private String remark;
    @Column()
    private ProtocolType protocol = ProtocolType.HTTP;
    @Column()
    private String host;
    @Column()
    private Integer port;
    @Column()
    private String url;

    @Column()
    private Boolean status = true;

    @Column()
    private String extra;

    @Column()
    @CreationTimestamp
    private Timestamp createTime;

    @Column()
    @UpdateTimestamp
    private Timestamp updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private Service service;
}
