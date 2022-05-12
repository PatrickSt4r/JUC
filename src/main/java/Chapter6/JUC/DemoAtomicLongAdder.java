package Chapter6.JUC;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
*
* */
@Slf4j(topic = "c.LongAdd")
public class DemoAtomicLongAdder {

    //0 没加锁
    //1 加锁
    private AtomicInteger state = new AtomicInteger(0);

    public void lock(){
        while (true){
            if(state.compareAndSet(0,1)){
                break;
            }
        }
    }

    public void unlock(){
        log.debug("unlock");
        state.set(0);
    }

    public static void main(String[] args) throws InterruptedException {
        DemoAtomicLongAdder lock = new DemoAtomicLongAdder();
        new Thread(()->{
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
                Slepper.sleep(1);
            }finally {
                lock.unlock();
            }
        }).start();
        new Thread(()->{
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
            }finally {
                lock.unlock();
            }
        }).start();
    }
}
