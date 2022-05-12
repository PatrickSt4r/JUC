package Chapter5.JMM01;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

/*
* 可见性 synchronized解决
* */
@Slf4j(topic = "c.JMM02")
public class DemoJMM02 {
    //解决办法:volatile
    volatile static boolean run = true;

    final static Object lock = new Object();
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                //业务逻辑
                synchronized (lock){
                    if (!run){
                        break;
                    }
                }
            }
        });
        t.start();
        Slepper.sleep(1);
        synchronized (lock){
            run = false;
        }

    }
}
