package wait;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* sleep vs wait
* */
@Slf4j(topic = "c.wait04")
public class DemoWait04 {

    final static Object lock= new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock){
                log.debug("获得锁");
                try {
                    //Thread.sleep(20000);  //sleep不会释放锁对象
                    lock.wait();                //wait会释放锁对象，其他线程可以获得锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        sleep(1);
        synchronized (lock){
            log.debug("获得锁");
        }

    }
}
