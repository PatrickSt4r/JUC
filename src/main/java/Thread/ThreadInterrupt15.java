package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 两阶段终止模式
* */
@Slf4j(topic = "c.test15")
public class ThreadInterrupt15 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        Thread.sleep(3000);
        tpt.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    private Thread monitor;

    public void start() {

        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if(currentThread.isInterrupted()){
                    log.debug("料理后事..");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控");
                } catch (InterruptedException e) {
                    currentThread.interrupt();
                    e.printStackTrace();
                }
            }
        }, "t1");

        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}
