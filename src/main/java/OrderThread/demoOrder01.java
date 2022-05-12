package OrderThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
* 运行顺序控制之ReentrantLock版
* */
@Slf4j(topic = "c.order01")
public class demoOrder01 {
    static final Object lock = new Object();
    //表示t2是否运行过
    static boolean t2runned = false;
    static final ReentrantLock LOCK = new ReentrantLock();
    public static void main(String[] args) {
        Condition condition1 =  LOCK.newCondition();
        Thread t1 = new Thread(() -> {
            LOCK.lock();
            try {
                if (!t2runned){
                    condition1.await();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            LOCK.lock();
            try {
                log.debug("2");
                t2runned = true;
                condition1.signal();
            }finally {
                LOCK.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
