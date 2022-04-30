package xit.gateway.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "service")
public class Service {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;

    @Column()
    private String app;

    @Column()
    private String remark;

    @Column()
    private Boolean status = true;

    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Route> routes;

    @Column()
    @CreationTimestamp
    private Timestamp createTime;

    @Column()
    @UpdateTimestamp
    private Timestamp updateTime;
}