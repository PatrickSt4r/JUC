package Chapter5.JMM01;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

/*
* 两阶段终止volatile解决
* */
@Slf4j(topic = "c.TJMM03")
public class DemoJMM03 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpw = new TwoPhaseTermination();
        tpw.start();
        Thread.sleep(3000);
        tpw.stop();
    }
}


@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    //监控线程
    private Thread monitor;

    private volatile boolean stop = false;

    public void start() {

        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                //是否被打断
                if(stop){
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                }
            }
        }, "t1");

        monitor.start();
    }

    public void stop(){
        stop = true;
        monitor.interrupt();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTerminationBalking {

    //监控线程
    private Thread monitor;

    private volatile boolean stop = false;

    //判断是否执行过start方法
    private boolean starting = false;

    public void start() {

        synchronized (this){
            if(starting){
                return;
            }
            starting = true;
        }
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                //是否被打断
                if(stop){
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                }
            }
        }, "t1");

        monitor.start();
    }

    public void stop(){
        stop = true;
        monitor.interrupt();
    }
}
