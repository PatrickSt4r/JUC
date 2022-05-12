package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.*;

/*
CyclicBarrier 示例
* */
@Slf4j(topic = "c.DemoCyclicBarrier")
public class DemoCyclicBarrier {
    public static void main(String[] args) {
        ExecutorService service = newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2,()->{
            log.debug("task1,task2 finish...");
        });
        for (int i = 0; i < 3; i++) {
            service.submit(()->{
                log.debug("task1 begin");
                Slepper.sleep(1);
                try {
                    barrier.await(); // 2 -1 = 1
                    log.debug("task1 end");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            service.submit(()->{
                log.debug("task2 begin");
                Slepper.sleep(2);
                try {
                    barrier.await(); // 1-1 = 0
                    log.debug("task2 end");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();

    }

    private static void method1() {
        ExecutorService service = newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);
        service.submit(()->{
            log.debug("task1 start");
            Slepper.sleep(1);
            latch.countDown();
        });
        service.submit(()->{
            log.debug("task2 start");
            Slepper.sleep(2);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("task1 task2 finish..");
        service.shutdown();
    }
}
