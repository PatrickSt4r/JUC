package Acitivity;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

/*
* 演示死锁
* */
@Slf4j(topic = "c.DeadLock01")
public class DemoDeadLock01 {
    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (A){
                log.debug("LOCK A");
                Slepper.sleep(1);
                synchronized (B){
                    log.debug("lock B");
                    log.debug("操作...");
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (B){
                log.debug("LOCK B");
                Slepper.sleep(0.5);
                synchronized (A){
                    log.debug("lock A");
                    log.debug("操作...");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
