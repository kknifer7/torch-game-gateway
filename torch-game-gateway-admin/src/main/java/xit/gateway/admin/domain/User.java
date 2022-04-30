package xit.gateway.admin.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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
    @JoinTable(name = "user_service",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "id")})
    private Set<Service> services;

}
