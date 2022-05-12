package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 线程状态
* */
@Slf4j(topic = "c.test16")
public class DemoThreadStatus16 {
    public static void main(String[] args) {
        //创建，但是没有start
        Thread t1 = new Thread(() -> {

        });

        //一直处于运行状态
        Thread t2 = new Thread(() -> {
            while (true){

            }
        });
        t2.start();

        //创建并打印
        Thread t3 = new Thread(() -> {
            log.debug("完事..");
        });
        t3.start();

        //timed_wating 有时限的等待
        Thread t4 = new Thread(() -> {
            synchronized (DemoThreadStatus16.class) {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t4.start();

        //t5等待t2，就是waiting状态了
        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t5.start();

        //t4已经提前拿到了锁，t6则一直在block状态
        Thread t6 = new Thread(() -> {
            synchronized (DemoThreadStatus16.class) {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("t1 state {}",t1.getState());
        log.debug("t2 state {}",t2.getState());
        log.debug("t3 state {}",t3.getState());
        log.debug("t4 state {}",t4.getState());
        log.debug("t5 state {}",t5.getState());
        log.debug("t6 state {}",t6.getState());

    }
}
