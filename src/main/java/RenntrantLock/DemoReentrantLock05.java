package RenntrantLock;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Slepper.sleep;

/*
* 条件变量-使用例子
* */
@Slf4j(topic = "c.ReentrantLock05")
public class DemoReentrantLock05 {

    static boolean hasCigaratte = false;
    static boolean hasTakeOut = false;
    static ReentrantLock ROOM = new ReentrantLock();
    //等待烟的休息室条件
    static Condition waitCigaretteSet = ROOM.newCondition();
    //等待外卖的休息室条件
    static Condition waitTakeoutSet = ROOM.newCondition();


    public static void main(String[] args) {
        new Thread(()->{
            ROOM.lock();
            try {
                log.debug("有烟没？[{}]",hasCigaratte);

                while(!hasCigaratte){
                    log.debug("没烟，摆");
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("烟到了，可以开始干活了");
            }finally {
                ROOM.unlock();
            }
        },"阿天").start();

        new Thread(()->{
            ROOM.lock();
            try {
                log.debug("外卖到了没？[{}]",hasTakeOut);
                if(!hasTakeOut){
                    log.debug("没外卖，摆");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了，可以开始干活了");
            }finally {
                ROOM.unlock();
            }
        },"玄子").start();

        sleep(1);

        new Thread(()->{
            ROOM.lock();
            try{
                hasTakeOut = true;
                waitTakeoutSet.signal();
                log.debug("外卖到了");
            }finally {
                ROOM.unlock();
            }
        },"外卖小哥").start();
        new Thread(()->{
            ROOM.lock();
            try{
                hasCigaratte = true;
                waitCigaretteSet.signal();
                log.debug("烟到了");
            }finally {
                ROOM.unlock();
            }
        },"阿咪").start();
    }
}
