package synchornized;

import lombok.extern.slf4j.Slf4j;

/*
* 面向对象优化synchronized
*
* */
@Slf4j(topic = "c.sync02")
public class DemoSync02 {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}",room.getCounter());

    }
}

class Room{
    private int counter = 0;

    public void increment(){
        synchronized (this){
            counter++;
        }
    }

    public void decrement(){
        synchronized (this){
            counter--;
        }
    }

    public int getCounter(){
        synchronized (this){
            return counter;
        }
    }
}
