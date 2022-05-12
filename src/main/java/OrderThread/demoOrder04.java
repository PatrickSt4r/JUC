package OrderThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/*
* 交替执行
* */
@Slf4j(topic = "c.order04")
public class demoOrder04 {
    public static void main(String[] args) {
        WatiNotify watiNotify = new WatiNotify(1, 5);
        Thread t1 = new Thread(() -> {
            try {
                watiNotify.print("a", 1, 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                watiNotify.print("b", 2, 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            try {
                watiNotify.print("c", 3, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
    }
}

/*
* 输出内容   等待标记    下一个标记
* a         1           2
* b         2           3
* c         3           1
* */
class WatiNotify{

    //打印
    public void print(String str,int waitFlag,int nextFlag) throws InterruptedException {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this){
                while(flag != waitFlag){
                    this.wait();
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    //等待标记
    private int flag;
    //循环次数
    private int loopNumber;

    public WatiNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}