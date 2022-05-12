package Thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/*
* 使用timeUnit使用slepp更直观
* */
@Slf4j(topic = "c.test")
public class DemoThread08 {
    public static void main(String[] args) throws InterruptedException {
        //等价于thread.sleep(xxms);
        log.debug("123");
        TimeUnit.SECONDS.sleep(1);
        log.debug("456");
    }
}
