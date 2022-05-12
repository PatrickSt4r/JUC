package OrderThread;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/*
* 交替执行之ParkUnpark版
* */
@Slf4j(topic = "c.order06")
public class demoOrder06 {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        ParkUnpark parkUnpark = new ParkUnpark(5);
        t1 = new Thread(()->{
            parkUnpark.print("a",t2);
        });
        t2 = new Thread(()->{
            parkUnpark.print("b",t3);
        });
        t3 = new Thread(()->{
            parkUnpark.print("c",t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}

class ParkUnpark {
    public void print(String str,Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.println(str);
            LockSupport.unpark(next);
        }
    }

    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}