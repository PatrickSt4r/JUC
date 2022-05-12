package Chapter8;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Slf4j(topic = "c.ForkJoin")
public class DemoForkJoin01 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTask(5)));
    }
}


//1-n之间整数的和
@Slf4j(topic = "c.Mytask")
class MyTask extends RecursiveTask<Integer>{
    private int n;

    public MyTask(int n){
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        //终止条件
        if(n == 1){
            log.debug("join() {}",n);
            return 1;
        }
        MyTask t1 = new MyTask(n - 1);
        t1.fork(); //让一个线程去执行此任务
        log.debug("fork() {}+{}",n,t1);

        int result =  n + t1.join(); //获取任务结果
        log.debug("join() {}+{} = {}",n,t1,result);
        return result;
    }
}