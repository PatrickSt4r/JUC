package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.Semaphore;

/*
semaphore演示
* */
@Slf4j(topic = "c.semaphore")
public class DemoSemaphore {
    public static void main(String[] args) {
        //1.创建Semaphore
        Semaphore semaphore = new Semaphore(3);

        //2.10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running....");
                    Slepper.sleep(1);
                    log.debug("end....");
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
