package xit.gateway.core.valve;

/**
 * @author Knifer
 * Description: 管道接口。用于快速在管道流中添加阀门
 * Date: 2022/03/25
 */
public interface Pipeline {
    Pipeline addLast(AbstractValve valve);
    Pipeline addFirst(AbstractValve valve);
}
