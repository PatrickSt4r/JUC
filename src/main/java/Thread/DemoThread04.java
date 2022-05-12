package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 演示多个线程并发交替执行
*
* */
@Slf4j(topic = "c.Test")
public class DemoThread04 {

    public static void main(String[] args) {

        new Thread(()->{
            while (true){
                log.debug("running1");
            }
        },"t1").start();
        new Thread(()->{
            while(true){
                log.debug("running2");
            }
        },"t2").start();

    }


}
