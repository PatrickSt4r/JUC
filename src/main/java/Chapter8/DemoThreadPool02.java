package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.SynchronousQueue;

/*
* 带缓冲的线程池
* */
@Slf4j(topic = "c.threadPool02")
public class DemoThreadPool02 {
    public static void main(String[] args) {
        SynchronousQueue<Integer> integers  = new SynchronousQueue<>();
        new Thread(()->{
            try {
                log.debug("putting{}",1);
                integers.put(1);
                log.debug("putted");

                log.debug("putting{}",2);
                integers.put(2);
                log.debug("putted");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        Slepper.sleep(1);

        new Thread(()->{
            try {
                log.debug("takeing{}",1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();

        Slepper.sleep(1);

        new Thread(()->{
            try {
                log.debug("takeing{}",2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();

    }
}
