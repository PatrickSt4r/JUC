package multiLock;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

/*
* 演示多把锁
* */
public class DemoMultiLock01 {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(()->{
            bigRoom.sleep();
        },"阿天").start();
        new Thread(()->{
            bigRoom.study();
        },"阿咪").start();
    }
}

@Slf4j(topic = "c.bigRoom")
class BigRoom{

    private final Object studyRoom = new Object();

    private final Object bedRoom = new Object();

    public void sleep(){
        synchronized (bedRoom){
            log.debug("睡俩小时");
            Slepper.sleep(2);
        }
    }

    public void study(){
        synchronized (studyRoom){
            log.debug("学一小时");
            Slepper.sleep(1);
        }
    }
}
