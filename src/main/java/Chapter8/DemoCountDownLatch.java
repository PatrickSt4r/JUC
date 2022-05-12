package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

/*
* countDownLatch演示
* */
@Slf4j(topic = "c.countdownlatch")
public class DemoCountDownLatch {


    public static void main(String[] args) throws InterruptedException {
        //应用模仿王者荣耀加载
        ExecutorService service = newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        Random r = new Random();
        String[] all = new String[10];
        for (int j = 0; j < 10; j++) {
            int k = j;
            service.submit(()->{
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[k] = i + "%";
                    System.out.print("\r" + Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n游戏开始");
        service.shutdown();
    }

    //改进
    private static void method2() {
        ExecutorService service = newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(3);
        service.submit(()->{
            log.debug("begin..");
            Slepper.sleep(1);
            latch.countDown();
            log.debug("end..{}",latch.getCount());
        });
        service.submit(()->{
            log.debug("begin..");
            Slepper.sleep(1.5);
            latch.countDown();
            log.debug("end..{}",latch.getCount());
        });
        service.submit(()->{
            log.debug("begin..");
            Slepper.sleep(2);
            latch.countDown();
            log.debug("end..{}",latch.getCount());
        });
        service.submit(()->{
            try {
                log.debug("waiting,,");
                latch.await();
                log.debug("wait end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void method1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        new Thread(()->{
            log.debug("begin..");
            Slepper.sleep(1);
            latch.countDown();
        }).start();
        new Thread(()->{
            log.debug("begin..");
            Slepper.sleep(2);
            latch.countDown();
        }).start();
        new Thread(()->{
            log.debug("begin..");
            Slepper.sleep(1.5);
            latch.countDown();
        }).start();
        log.debug("waiting,,");
        latch.await();
        log.debug("wait end");
    }
}
