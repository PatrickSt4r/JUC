package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* sleep
*   1.调用sleep会让当前线程从Running进入Timed Waiting状态
    2.其他线程可以使用interrupt方法打断正在睡眠的线程，这时sleep方法会抛出InterruptedException
    3.睡眠结束后的线程未必会立刻得到执行
* */
@Slf4j(topic = "c.Test")
public class DemoThread07 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread("t1") {
            @Override
            public void run() {
                try {
                    log.debug("enter sleeping...");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        Thread.sleep(500);
        log.debug("interrupt...");
        thread.interrupt();
    }
}
