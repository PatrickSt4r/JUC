package synchornized;

import lombok.extern.slf4j.Slf4j;

/*
* 共享问题
* */
@Slf4j(topic = "c.sync01")
public class DemoSync01 {
    static int counter = 0;
    static Object room = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room){
                    counter++;
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room){
                    counter--;
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}",counter);

    }
}
