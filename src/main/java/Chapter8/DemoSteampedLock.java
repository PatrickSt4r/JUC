package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.locks.StampedLock;

/*
* 演示stampedLock
* */
public class DemoSteampedLock {
    public static void main(String[] args) {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(()->{
            dataContainer.read(1);
        },"t1").start();
        Slepper.sleep(0.5);
        new Thread(()->{
            dataContainer.write(100);
        },"t2").start();
    }
}

@Slf4j(topic = "c.DataContraniterStamped")
class DataContainerStamped{
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data){
        this.data = data;
    }

    public int read(int readTime){
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking... {}",stamp);
        Slepper.sleep(readTime);
        if(lock.validate(stamp)){
            log.debug("read finish ..{}",stamp);
            return data;
        }
        //锁升级 - 读锁
        log.debug("upadating to read locking.. {}",stamp);
        try {
            lock.readLock();
            log.debug("read lock {}",stamp);
            Slepper.sleep(readTime);
            log.debug("read fininsh .. {}",stamp);
            return data;
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData){
        long stamp = lock.writeLock();
        log.debug("write lock {}",stamp);

        try {
            Slepper.sleep(2);
            this.data = newData;
        } finally {
            log.debug("write unlock {}",stamp);
            lock.unlockWrite(stamp);
        }

    }

}