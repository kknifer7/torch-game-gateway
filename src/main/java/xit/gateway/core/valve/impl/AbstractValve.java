package xit.gateway.core.valve.impl;

import xit.gateway.core.exception.IllegalValveOperationException;
import xit.gateway.core.valve.Valve;

/**
 * @author knifer
 * Description: 阀门的抽象类
 * Date: 2022/03/22
 */
public abstract class AbstractValve implements Valve {
    protected AbstractValve previous;
    protected AbstractValve next;
    protected boolean done;

    public AbstractValve(){
        done = false;
    }

    public AbstractValve(AbstractValve previous, AbstractValve next){
        this();
        this.previous = previous;
        this.next = next;
    }

    @Override
    public boolean isDone(){
        return done;
    }

    @Override
    public void run(){
        validateValveNotDone("can not run this valve: valve is done");
        work();
        done = true;
        if (hasNext()){
            next();
        }
    }

    private void validateValveNotDone(String msg){
        if (isDone()){
            throw new IllegalValveOperationException(msg);
        }
    }

    @Override
    public abstract AbstractValve addBefore(AbstractValve valve);

    @Override
    public abstract AbstractValve addAfter(AbstractValve valve);

    /**
     * 阀门的核心逻辑
     */
    protected abstract void work();

    /**
     * 返回是否存在下一个阀门
     * @return 是否存在下一个阀门
     */
    protected boolean hasNext(){
        return next != null;
    }

    /**
     * 执行下一个阀门
     */
    protected void next(){
        next.run();
    }
}
