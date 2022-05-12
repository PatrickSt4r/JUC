package Chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.*;

/*
固定大小线程池
* */
@Slf4j(topic = "c.TestThreadPoolExecutor")
public class DemoThreadPool {
    public static void main(String[] args) {
        ExecutorService pool = newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"mypool_t" + t.getAndDecrement());
            }
        });

        pool.execute(()->{
            log.debug("1");
        });

        pool.execute(()->{
            log.debug("2");
        });

        pool.execute(()->{
            log.debug("3");
        });
    }
}
