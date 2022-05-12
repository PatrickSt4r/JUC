package wait;

import lombok.extern.slf4j.Slf4j;

/*
* 演示wait
* 需要获取锁之后才能调用wait，否则报错
* */
@Slf4j(topic = "c.wait01")
public class DemoWait01 {
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        synchronized (lock){
            lock.wait();
        }
    }
}
