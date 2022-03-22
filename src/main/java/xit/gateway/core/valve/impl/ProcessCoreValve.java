package xit.gateway.core.valve.impl;

import xit.gateway.core.exception.IllegalValveOperationException;

/**
 * @author knifer
 * Description: 核心流程阀门。执行顺序固定，在确定后就不允许改变
 * Date: 2022/03/22
 */
public abstract class ProcessCoreValve extends AbstractValve{

    public ProcessCoreValve(){}


    @Override
    public AbstractValve addBefore(AbstractValve valve) {
        if (previous != null || valve.next != null){
            throw new IllegalValveOperationException("core valve sequence cannot be changed");
        }
        previous = valve;
        valve.next = this;

        return valve;
    }

    @Override
    public AbstractValve addAfter(AbstractValve valve) {
        if (this.next != null || valve.previous != null){
            throw new IllegalValveOperationException("core valve sequence cannot be changed");
        }
        next = valve;
        valve.previous = this;

        return valve;
    }
}
