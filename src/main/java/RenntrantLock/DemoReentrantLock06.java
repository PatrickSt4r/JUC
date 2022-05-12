package RenntrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Slepper.sleep;

/*
* 条件变量
* */
@Slf4j(topic = "c.ReentrantLock06")
public class DemoReentrantLock06{
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        //创建一个新的条件变量(休息室)
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();
        //进入休息室等待
        condition1.await();

        //叫醒条件中某一个线程
        condition1.signal();
        //叫醒条件中所有线程
        condition1.signalAll();

    }
}
