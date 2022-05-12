package Chapter8;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
* 读写锁演示
* */
@Slf4j(topic = "c.DemoReentrantReadWriteLock")
public class DemoReentrantReadWriteLock {
    public static void main(String[] args) throws InterruptedException {
        DataContainer dataContainer = new DataContainer();
        new Thread(()->{
            dataContainer.read();
            Slepper.sleep(1);
        },"t1").start();
        Thread.sleep(100);
        new Thread(()->{
            dataContainer.write();
            Slepper.sleep(1);
        },"t2").start();
    }
}

@Slf4j(topic = "c.DataContainer")
class DataContainer{
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read(){
        log.debug("获取读锁..");
        r.lock();
        try {
            log.debug("读取");
            return data;
        }finally {
            log.debug("释放读锁...");
            r.unlock();
        }
    }

    public void write(){
        log.debug("获取写锁...");
        w.lock();
        try {
            log.debug("写入");
        } finally {
            log.debug("释放写锁...");
            w.unlock();
        }
    }
}