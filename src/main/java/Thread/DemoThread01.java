package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 线程创建
* 方法1
*
* */
@Slf4j(topic = "c.Test")
public class DemoThread01 {

    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t1.setName("t1");
        t1.start();
        log.debug("running");
    }
}
