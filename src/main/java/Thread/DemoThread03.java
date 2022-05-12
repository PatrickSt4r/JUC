package Thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
* 创建线程方法3
* 
* */
@Slf4j(topic = "c.Test3")
public class DemoThread03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(2000);
                return 100;
            }
        });

        Thread t1 = new Thread(task, "T1");
        t1.start();
        task.get();
        log.debug("{}",task.get());

    }

}
