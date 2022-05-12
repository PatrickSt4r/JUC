package Thread;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* 多线程的统筹规划
* */
@Slf4j(topic = "c.test16")
public class DemoThread16 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            sleep(1);
            log.debug("烧开水");
            sleep(5);
        }, "阿天");

        t1.start();

        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            sleep(1);
            log.debug("洗茶杯");
            sleep(2);
            log.debug("拿茶叶");
            sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
        }, "阿咪");

        t2.start();

    }
}
