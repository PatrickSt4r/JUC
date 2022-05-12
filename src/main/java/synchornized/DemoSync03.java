package synchornized;

import lombok.extern.slf4j.Slf4j;

/*
* 方法上的synchronized
**/
@Slf4j(topic = "c.sync03")
public class DemoSync03 {

    public static void main(String[] args) throws InterruptedException {
        Room2 room2 = new Room2();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room2.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room2.decrement();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}",room2.getCounter());

    }
}

class Room2{
    private int counter = 0;

    public synchronized void increment(){
            counter++;
    }

    public synchronized void decrement(){
            counter--;
    }

    public synchronized int getCounter(){
            return counter;
    }
}