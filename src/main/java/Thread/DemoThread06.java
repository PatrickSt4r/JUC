package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* start和run
* */
@Slf4j(topic = "c.Test")
public class DemoThread06 {
    public static void main(String[] args) {
        Thread  t1= new Thread("t1") {
            @Override
            public void run() {
                log.debug("running");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
//        t1.run();
        log.debug("做其他事");
    }
}
