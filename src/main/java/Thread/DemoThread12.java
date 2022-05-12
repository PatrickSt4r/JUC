package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 线程等待多同步
* */
@Slf4j(topic = "c.test12")
public class DemoThread12 {

    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;


    public static void main(String[] args) throws InterruptedException {
//        test2();
        test3();
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;

        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r2 = 20;
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        log.debug("join begin");
        t1.join();
        log.debug("t1 join end");
        t2.join();
        log.debug("t2 join end");
        long end = System.currentTimeMillis();
        log.debug("r1:{} r2:{} cost:{}" ,r1 ,r2 ,end - start);
    }

    //join时效性
    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                r1 = 10;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long start = System.currentTimeMillis();
        t1.start();
        //线程执行结果会导致join结束
        log.debug("join begin");
        t1.join(1500);
        long end = System.currentTimeMillis();
        log.debug("r1:{} r2:{} cost:{}" ,r1 ,r2 ,end - start);
    }
}
