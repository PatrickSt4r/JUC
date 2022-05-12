package wait;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* wait应用 SETP1
* */
@Slf4j(topic = "c.wait05")
public class DemoWait05 {
    static final Object room = new Object();
    static boolean hasCigaratte = false;
    static boolean hasTakeOut = false;

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (room){
                log.debug("有烟没？[{}]",hasCigaratte);
                if(!hasCigaratte){
                    log.debug("没烟，摆");
                    sleep(2);
                }
                log.debug("有烟没？[{}]",hasCigaratte);
                if(hasCigaratte){
                    log.debug("可以开始干活了");
                }
            }
        },"阿天").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (room){
                    log.debug("可以开始干活了");
                }
            },"其他人"+i).start();
        }

        sleep(1);
        new Thread(()->{
            hasCigaratte = true;
            log.debug("烟到了");
        },"阿咪").start();
    }
}
