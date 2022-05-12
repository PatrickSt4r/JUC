package wait;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* 带参数的wait
* wait第一个参数为毫秒值，线程等待多少毫秒之后继续执行
* */
@Slf4j(topic = "c.wait03")
public class DemoWait03 {
    final static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (obj){
                log.debug("执行....");
                try {
                    obj.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t1继续执行");
            }
        },"t1").start();

        sleep(0.5);//如果在之前被唤醒，则继续执行
        synchronized (obj){
            obj.notify();
        }

    }
}
