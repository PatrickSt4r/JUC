package synchornized;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* 线程八锁
*
* */
@Slf4j(topic = "c.sync04")
public class DemoSync04 {
    public static void main(String[] args) {
        //锁的都是this 就是n1对象，互斥
        Number1 n1 = new Number1();
        new Thread(()->{
            log.debug("begin");
            n1.a();
        }).start();
        new Thread(()->{
            log.debug("begin");
            n1.b();
        }).start();

        //情况4
        Number1 n2 = new Number1();
        new Thread(()->{
            n1.a();
        }).start();
        new Thread(()->{
            n2.b();
        }).start();
    }
}

//情况1
@Slf4j(topic = "c.Number1")
class Number1{
    public synchronized void a(){
        log.debug("1");
    }

    public synchronized void b(){
        log.debug("2");
    }
}

//情况2
//在a方法多加了一个sleep1秒
@Slf4j(topic = "c.Number2")
class Number2{
    public synchronized void a(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("1");
    }

    public synchronized void b(){
        log.debug("2");
    }
}

//情况3
@Slf4j(topic = "c.Number3")
class Number3{
    public synchronized void a(){
        sleep(1);
        log.debug("1");
    }

    public synchronized void b(){
        log.debug("2");
    }

    public void c(){
        log.debug("3");
    }
}


//情况5
@Slf4j(topic = "c.Number4")
class Number4{
    public static synchronized void a(){
        sleep(1);
        log.debug("1");
    }

    public synchronized void b(){
        log.debug("2");
    }

}

//情况6
@Slf4j(topic = "c.Number5")
class Number5{
    public static synchronized void a(){
        sleep(1);
        log.debug("1");
    }
    public static synchronized void b(){
        log.debug("2");
    }
}

