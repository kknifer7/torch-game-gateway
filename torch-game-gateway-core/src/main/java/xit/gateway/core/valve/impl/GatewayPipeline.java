package xit.gateway.core.valve.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.api.valve.*;

/**
 * @author Knifer
 * Description: 管道流单例
 * Date: 2022/03/25
 */
@Component
public class GatewayPipeline implements Pipeline {
    private Valve first;
    private Valve last;

    @Autowired
    private GatewayPipeline(RouteInitializationLoadingValve routeLoadingValve, HeartBeatServiceValve heartBeatServiceValve){
        first = new ProcessCoreValve() {@Override protected void work() {}};
        last = new ProcessCoreValve() {@Override protected void work() {}};
        this.addLast(routeLoadingValve)
            .addLast(heartBeatServiceValve);
        this.first.run();
    }

    @Override
    public Pipeline addLast(AbstractValve valve) {
        last.addAfter(valve);
        last = valve;

        return this;
    }

    @Override
    public Pipeline addFirst(AbstractValve valve) {
        first.addBefore(valve);
        first = valve;

        return this;
    }
}
