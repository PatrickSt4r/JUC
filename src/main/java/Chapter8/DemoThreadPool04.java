package Chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

/*
* 提交任务
* */
@Slf4j(topic = "c.Submit")
public class DemoThreadPool04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = newFixedThreadPool(2);

        Future<Integer> result1 = pool.submit(() -> {
            log.debug("task 1 running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task 1 fininsh...");
            return 1;
        });

        Future<Integer> result2 = pool.submit(() -> {
            log.debug("task 2 running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task 2 fininsh...");
            return 2;
        });

        Future<Integer> result3 = pool.submit(() -> {
            log.debug("task 3 running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task 3 fininsh...");
            return 1;
        });

        log.debug("showdown");
//        pool.shutdown();
//        pool.awaitTermination(3, TimeUnit.SECONDS);
        List<Runnable> runnables = pool.shutdownNow();
        log.debug("ohter..{}",runnables);
    }

    //sumbit
    private static void method1(ExecutorService pool) throws ExecutionException, InterruptedException{
        Future<String> submit = pool.submit(()-> {
            log.debug("running");
            Thread.sleep(1000);
            return "ok";
        });
        log.debug("{}",submit.get());
    }

    //invokeAll
    private static void method2(ExecutorService pool) throws ExecutionException, InterruptedException{
        List<Future<Object>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    log.debug("begin");
                    Thread.sleep(1000);
                    return "1";
                },
                () -> {
                    log.debug("begin");
                    Thread.sleep(500);
                    return "2";
                },
                () -> {
                    log.debug("begin");
                    Thread.sleep(2000);
                    return "3";
                }
        ));

        futures.forEach( f ->{
            try {
                log.debug("{}",f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static void method3(ExecutorService pool) throws ExecutionException, InterruptedException{
        //invokeAny
        String result = pool.invokeAny(Arrays.asList(
                () -> {
                    log.debug("begin 1");
                    Thread.sleep(1000);
                    log.debug("end 1");
                    return "1";
                },
                () -> {
                    log.debug("begin 2");
                    Thread.sleep(500);
                    log.debug("end 2");
                    return "2";
                },
                () -> {
                    log.debug("begin 3");
                    Thread.sleep(2000);
                    log.debug("end 3");
                    return "3";
                }
        ));

        log.debug("{}",result);
    }
}
