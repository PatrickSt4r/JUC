package ThreadTrans;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* 线程转换
*
* */
@Slf4j(topic = "c.trans01")
public class DemoThreadTrans01 {
    final static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (obj){
                log.debug("执行t1...");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t1其他代码...");
            }
        },"t1").start();

        new Thread(()->{
            synchronized (obj){
                log.debug("执行t2..");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2其他代码...");
            }
        },"t2").start();

        sleep(0.5);

        log.debug("唤醒obj上其他线程");
        synchronized (obj){
            obj.notifyAll();
        }

    }
}
