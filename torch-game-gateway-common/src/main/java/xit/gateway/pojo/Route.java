package xit.gateway.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import xit.gateway.constant.ProtocolType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Knifer
 * Description: 路由信息类
 * Date: 2022/03/25
 */
@Entity
@Getter
@Setter
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private String id;
    @Column()
    private String name;
    @Column()
    private String desc;
    @Column()
    private ProtocolType protocol;
    @Column()
    private String host;
    @Column()
    private int port;
    @Column()
    private String url;
    @Column()
    private boolean status;

    @Column()
    @CreatedDate
//    @JsonSerialize(using = LocalTimeSerializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDatetime;

    @Column()
    @LastModifiedDate
//    @JsonSerialize(using = LocalTimeSerializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDatetime;

    @Column()
    private String extra;

    public boolean getStatus() {
        return status;
    }
}
