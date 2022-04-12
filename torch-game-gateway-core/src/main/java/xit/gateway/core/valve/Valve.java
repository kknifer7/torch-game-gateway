package xit.gateway.core.valve;

/**
 * @author knifer
 * Description: 阀门接口。“阀门”是责任链上的一个结点
 * Date: 2022/03/22
 */
public interface Valve {
    /**
     * 运行这个阀门
     */
    void run();

    /**
     * 返回阀门是否已执行完成
     * @return 阀门是否已执行完成
     */
    boolean isDone();

    /**
     * 在之前添加阀门
     * @param valve 要添加的阀门
     * @return 返回被添加的阀门
     */
    AbstractValve addBefore(AbstractValve valve);

    /**
     * 在之后添加阀门
     * @param valve 要添加的阀门
     * @return 返回被添加的阀门
     */
    AbstractValve addAfter(AbstractValve valve);
}
