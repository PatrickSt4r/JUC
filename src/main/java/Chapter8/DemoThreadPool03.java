package Chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.*;


/*
* 单线程线程池
* */
@Slf4j(topic = "c.SingleThreadPool")
public class DemoThreadPool03 {
    public static void main(String[] args) {
        test2();
    }

    public static void test2(){
        ExecutorService pool = newSingleThreadExecutor();
        pool.execute(()->{
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(()->{
            log.debug("2");
        });

        pool.execute(()->{
            log.debug("3");
        });
    }
}
