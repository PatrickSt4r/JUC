package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 打断阻塞中的线程
* sleep,wait,join打断时候打断标记会置为false
* */
@Slf4j(topic = "c.test13")
public class ThreadInterrupt13 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep..");
            try {
                Thread.sleep(5000);//wait,join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记:{}",t1.isInterrupted());

    }
}
