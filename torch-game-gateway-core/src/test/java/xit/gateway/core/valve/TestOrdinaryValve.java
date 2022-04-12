package xit.gateway.core.valve;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestOrdinaryValve {
    @Test
    public void testOrdinaryValveAdd(){
        OrdinaryValve ordinaryValve0 = new OrdinaryValve() {
            @Override
            protected void work() {
                System.out.println("普通0");
            }
        };
        OrdinaryValve ordinaryValve1 = new OrdinaryValve() {
            @Override
            protected void work() {
                System.out.println("普通1");
            }
        };
        OrdinaryValve ordinaryValve2 = new OrdinaryValve() {
            @Override
            protected void work() {
                System.out.println("普通2");
            }
        };

        ordinaryValve0.addAfter(ordinaryValve1)
                        .addBefore(ordinaryValve2);

        ordinaryValve0.run();
    }

    @Test
    public void testProcessCoreValveAdd(){
        ProcessCoreValve processCoreValve0 = new ProcessCoreValve() {
            @Override
            protected void work() {
                System.out.println("核心0");
            }
        };
        ProcessCoreValve processCoreValve1 = new ProcessCoreValve() {
            @Override
            protected void work() {
                System.out.println("核心1");
            }
        };
        ProcessCoreValve processCoreValve2 = new ProcessCoreValve() {
            @Override
            protected void work() {
                System.out.println("核心2");
            }
        };
        OrdinaryValve ordinaryValve0 = new OrdinaryValve() {
            @Override
            protected void work() {
                System.out.println("普通0");
            }
        };

        processCoreValve2.addBefore(processCoreValve1)
                        .addBefore(processCoreValve0)
                        .addBefore(ordinaryValve0);

        ordinaryValve0.run();
    }
}
