package wait;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* 演示notify和notifyAll的使用场景
*
* */
@Slf4j(topic = "c.wait02")
public class DemoWait02 {
    final static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (obj){
                log.debug("执行....");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t1继续执行");
            }
        },"t1").start();

        new Thread(()->{
            synchronized (obj){
                log.debug("执行2..");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2继续执行..");
            }
        },"t2").start();

        //主线程两秒后执行
        sleep(2);
        log.debug("唤醒obj上的其他线程");
        synchronized (obj){
           // obj.notify(); //挑一个唤醒
            obj.notifyAll();//唤醒所有线程
        }
    }

}
