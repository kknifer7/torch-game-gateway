package xit.gateway.core.valve.impl;

import xit.gateway.core.valve.Pipeline;
import xit.gateway.core.valve.Valve;

/**
 * @author Knifer
 * Description: 管道流单例
 * Date: 2022/03/25
 */
public class GatewayPipeline implements Pipeline {
    private final static GatewayPipeline PIPELINE = new GatewayPipeline();
    private final Valve first;
    private final Valve last;

    private GatewayPipeline(){
        // TODO 初始化管道流
        first = null;
        last = null;
    }

    public static GatewayPipeline getInstance(){
        return PIPELINE;
    }

    @Override
    public Pipeline addLast() {
        return null;
    }

    @Override
    public Pipeline addFirst() {
        return null;
    }

    @Override
    public Pipeline addAfter(Valve valve, Valve newValve) {
        return null;
    }

    @Override
    public Pipeline addBefore(Valve valve, Valve newValve) {
        return null;
    }
}
