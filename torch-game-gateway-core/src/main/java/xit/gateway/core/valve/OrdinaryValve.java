package xit.gateway.core.valve;

import xit.gateway.core.exception.valve.IllegalValveOperationException;

/**
 * @author knifer
 * Description: 常规阀门。次要功能逻辑，可以调整顺序
 * Date: 2022/03/22
 */
public abstract class OrdinaryValve extends AbstractValve {
    public OrdinaryValve() {}

    public OrdinaryValve(OrdinaryValve previous, OrdinaryValve next) {
        super(previous, next);
    }

    @Override
    public AbstractValve addBefore(AbstractValve valve) {
        validateOrdinaryValve(valve, "core valve sequence cannot be changed");

        if (previous != null){
            previous.next = valve;
        }
        valve.previous = previous;
        valve.next = this;
        this.previous = valve;

        return valve;
    }

    @Override
    public AbstractValve addAfter(AbstractValve valve) {
        validateOrdinaryValve(valve, "core valve sequence cannot be changed");

        if (next != null){
            next.previous = valve;
        }
        valve.next = next;
        valve.previous = this;
        this.next = valve;

        return valve;
    }

    private void validateOrdinaryValve(Valve valve, String msg) {
        if (!(valve instanceof OrdinaryValve)) {
            throw new IllegalValveOperationException(msg);
        }
    }
}
