package xit.gateway.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author Knifer
 * Date: 2022/05/04
 * Description: 限流器实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Limiter {
    private Serializable id;
    private Long limit;
    private Long limitingTimeout;
    private TimeUnit limitingTimeUnit;
}
