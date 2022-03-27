package xit.gateway.core.valve.impl;

import xit.gateway.exception.valve.IllegalValveOperationException;

/**
 * @author knifer
 * Description: 核心流程阀门。执行顺序固定，在确定后就不允许改变
 * （可以使用核心流程阀门将普通阀门加入到整个核心流程的开头或结尾，但整个核心加载流程不允许被普通阀门打断）
 * Date: 2022/03/22
 */
public abstract class ProcessCoreValve extends AbstractValve{

    public ProcessCoreValve(){}

    @Override
    public AbstractValve addBefore(AbstractValve valve) {
        if (previous != null || (valve.next != null && !(valve instanceof OrdinaryValve))){
            throw new IllegalValveOperationException("core valve sequence cannot be changed");
        }
        previous = valve;
        valve.next = this;

        return valve;
    }

    @Override
    public AbstractValve addAfter(AbstractValve valve) {
        if (next != null || (valve.previous != null && !(valve instanceof OrdinaryValve))){
            throw new IllegalValveOperationException("core valve sequence cannot be changed");
        }
        next = valve;
        valve.previous = this;

        return valve;
    }
}
