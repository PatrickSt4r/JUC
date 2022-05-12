package Chapter5.JMM01;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

/*
* 可见性
* */
@Slf4j(topic = "c.JMM01")
public class DemoJMM01 {
    //解决办法:volatile
    volatile static boolean run = true;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (run) {
                //业务逻辑
            }
        });
        t.start();
        Slepper.sleep(1);
        run = false; //线程不会如预想的停下来(不加volatile)

    }
}
