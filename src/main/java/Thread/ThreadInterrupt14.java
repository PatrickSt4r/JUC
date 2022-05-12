package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 打断正常标记
* */
@Slf4j(topic = "c.test14")
public class ThreadInterrupt14 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted){
                    log.debug("被打断，退出循环");
                    break;
                }
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrrupt");
        t1.interrupt();
    }
}
