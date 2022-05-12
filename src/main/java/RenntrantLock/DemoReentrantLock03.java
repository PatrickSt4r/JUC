package RenntrantLock;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
* 锁超时
* */
@Slf4j(topic = "c.ReentrantLock03")
public class DemoReentrantLock03 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
            try {
                if(!lock.tryLock(2, TimeUnit.SECONDS)){
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁");
                return;
            }
            try {
                log.debug("获得到锁");
            }finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("main获得到锁");
        t1.start();
        Slepper.sleep(1);
        log.debug("释放了锁");
        lock.unlock();

    }
}
