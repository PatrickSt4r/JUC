package Chapter8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.*;

/*
* 演示饥饿现象
* */
@Slf4j(topic = "c.starve")
public class DemoDeadLock {

    static final List<String> MENU = Arrays.asList("地三鲜","宫保鸡丁","辣子鸡丁","烤鸡腿");

    static Random RANDOM = new Random();

    static String cooking() {return MENU.get(RANDOM.nextInt(2));}

    public static void main(String[] args) {
        ExecutorService waiterPool = newFixedThreadPool(1);
        ExecutorService cookPool = newFixedThreadPool(1);

        waiterPool.execute(()->{
            log.debug("处理点餐");
            Future<String> f = cookPool.submit(() -> {
                log.debug("做菜");
                return cooking();
            });
            try {
                log.debug("上菜 {}",f.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        waiterPool.execute(()->{
            log.debug("处理点餐");
            Future<String> f = cookPool.submit(() -> {
                log.debug("做菜");
                return cooking();
            });
            try {
                log.debug("上菜 {}",f.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
