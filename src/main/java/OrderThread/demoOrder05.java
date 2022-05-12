package OrderThread;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
* 交替执行之ReentrantLock版
* */
@Slf4j(topic = "c.order05")
public class demoOrder05 {
    public static void main(String[] args) {
        AwaitSingal awaitSingal = new AwaitSingal(5);
        Condition a = awaitSingal.newCondition();
        Condition b = awaitSingal.newCondition();
        Condition c = awaitSingal.newCondition();
        new Thread(()->{
            awaitSingal.print("a",a,b);
        }).start();
        new Thread(()->{
            awaitSingal.print("b",b,c);
        }).start();
        new Thread(()->{
            awaitSingal.print("c",c,a);
        }).start();
        Slepper.sleep(1);
        awaitSingal.lock();
        try {
            log.debug("开始..");
            a.signal();
        }finally {
            awaitSingal.unlock();
        }
    }
}

class AwaitSingal extends ReentrantLock {
    private int loopNumber;

    public AwaitSingal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str,Condition current,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}