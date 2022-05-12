package OrderThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/*
* 运行顺序控制之park unpark版
* */
@Slf4j(topic = "c.order03")
public class demoOrder03 {
    static final Object lock = new Object();
    //表示t2是否运行过
    static boolean t2runned = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t2");

        t1.start();
        t2.start();
    }
}
