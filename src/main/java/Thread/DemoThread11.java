package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 演示join
* */
@Slf4j(topic = "c.test11")
public class DemoThread11 {

    static int r = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread thread = new Thread(() -> {
            log.debug("t1开始");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1结束");
            r = 10;
        }, "t1");
        thread.start();
        thread.join();
        log.debug("结果为:{}",r);
        log.debug("结束");
    }

}
