package Acitivity;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

/*
* 活锁
* */
@Slf4j(topic = "c.LiveLock")
public class DemoDeadLock03 {
    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            //期望减到0 退出循环
            while (count>0){
                Slepper.sleep(0.2);
                count --;
                log.debug("count:{}",count);
            }
        },"t1").start();

        new Thread(()->{
            while (count<20){
                //期望超过20 退出循环
                Slepper.sleep(0.2);
                count ++;
                log.debug("count:{}",count);
            }
        },"t2").start();
    }
}
