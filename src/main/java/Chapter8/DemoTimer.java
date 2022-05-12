package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

/*
* 任务调度线程池
* */
@Slf4j(topic = "c.timer")
public class DemoTimer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = newScheduledThreadPool(1);
        log.debug("start...");
        //period保证至少xx秒后执行，如果方法内由Sleep大于period，则按最大时间执行
//        pool.scheduleAtFixedRate(()->{
//            log.debug("running...");
//            Slepper.sleep(2);
//        },1,1,TimeUnit.SECONDS);

        //从任务结束开始算
//        pool.scheduleWithFixedDelay(()->{
//            log.debug("running...");
//            Slepper.sleep(2);
//        },1,1,TimeUnit.SECONDS);

        ExecutorService pool2 = newFixedThreadPool(1);

        Future<Boolean> fu = pool2.submit(() -> {
            log.debug("task1");
            int i = 1 / 0;
            return true;
        });
        log.debug("result:{}",fu.get());

//        method2(pool);
    }

    public static void method2(ScheduledExecutorService pool){
        pool.schedule(()->{
            log.debug("task1");
            try {
                int i = 1/0;
            }catch (Exception e){
                log.error("error:",e);
            }
//            Slepper.sleep(2);
        },1, TimeUnit.SECONDS);
        pool.schedule(()->{
            log.debug("task2");
        },1, TimeUnit.SECONDS);
    }

    public static void method1(){
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
//                Slepper.sleep(2);
                int i = 1/0;
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };
        log.debug("start..");
        timer.schedule(task1,1000);
        timer.schedule(task2,1000);
    }
}
