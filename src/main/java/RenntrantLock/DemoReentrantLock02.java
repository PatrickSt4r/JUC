package RenntrantLock;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.locks.ReentrantLock;

/*
* 演示可重入
* */
@Slf4j(topic = "c.ReentrantLock02")
public class DemoReentrantLock02 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                //如果没有竞争，那么此方法就会获取lock对象锁
                //如果有竞争，则进入阻塞队列
                log.debug("尝试获得锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁,返回");
                return;
            }
            try {
                log.debug("获取到锁");
            }finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        t1.start();
        Slepper.sleep(1);
        log.debug("打断t1");
        t1.interrupt();
    }
}
