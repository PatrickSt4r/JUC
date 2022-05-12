package Chapter8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/*
* 定时任务
* */
public class DemoTimerApply {

    //如何让每周四18:00:00定时执行任务
    public static void main(String[] args) {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();

        //获取周四时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);

        //如果 当前时间 > 本周周四，必须找到下周周四
        if(now.compareTo(time) > 0){
            time = time.plusWeeks(1);
        }
        System.out.println(now);
        System.out.println(time);

        long initDelay = Duration.between(now, time).toMillis();
        ScheduledExecutorService pool = newScheduledThreadPool(1);
//        //initailDelay 代表当前时间和周四的差值
//        //period 一周时间间隔
        long period = 1000 * 60 * 60 * 24 * 7;
        pool.scheduleAtFixedRate(()->{
            System.out.println("running...");
        },initDelay,period, TimeUnit.MILLISECONDS);
    }
}
