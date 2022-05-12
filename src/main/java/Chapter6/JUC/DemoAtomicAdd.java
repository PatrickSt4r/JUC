package Chapter6.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
* 原子累加器
* */
@Slf4j(topic = "c.add")
public class DemoAtomicAdd {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            demo(
                    ()->new AtomicLong(0),
                    (adder)-> adder.getAndIncrement()
            );
        }

        for (int i = 0; i < 5; i++) {
            demo(
                    ()->new LongAdder(),
                    adder->adder.increment()
            );
        }


    }

    /*
     * ()->结果  提供累加器对象
    * (参数) ——> 执行累加操作
    * */
    private static <T> void demo(Supplier<T> addSupplier, Consumer<T> action) throws InterruptedException {
        T adder = addSupplier.get();
        List<Thread> ts = new ArrayList<>();
        //4个线程，每个人累加50w
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(()->{
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        for (Thread t : ts) {
            t.join();
        }
        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end-start)/1000_000);
    }
}
