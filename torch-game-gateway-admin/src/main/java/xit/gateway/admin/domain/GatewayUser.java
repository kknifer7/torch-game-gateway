package xit.gateway.admin.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "gateway_user")
public class GatewayUser implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "my_id", strategy = "xit.gateway.admin.utils.MyIdGenerator")
    @GeneratedValue(generator = "my_id")
    private String id;

    @Column()
    private String username;

    @Column()
    private String pwd;


    @Column()
    @CreationTimestamp
    private Timestamp createTime;

    @Column()
    @UpdateTimestamp
    private Timestamp updateTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gateway_user_route",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "route_id", referencedColumnName = "id")})
    private Set<Route> routes;

}
