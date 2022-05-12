package wait;

import lombok.extern.slf4j.Slf4j;

import static utils.Slepper.sleep;

/*
* wait应用 SETP4
* 用while替换if 解决虚假唤醒
* */
@Slf4j(topic = "c.wait08")
public class DemoWait08 {
    static final Object room = new Object();
    static boolean hasCigaratte = false;
    static boolean hasTakeOut = false;

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (room){
                log.debug("有烟没？[{}]",hasCigaratte);
                while(!hasCigaratte){
                    log.debug("没烟，摆");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]",hasCigaratte);
                if(hasCigaratte){
                    log.debug("可以开始干活了");
                }else {
                    log.debug("搞个几把");
                }
            }
        },"阿天").start();

        new Thread(()->{
            synchronized (room){
                log.debug("外卖到了没？[{}]",hasTakeOut);
                if(!hasTakeOut){
                    log.debug("没外卖，摆");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了没？[{}]",hasTakeOut);
                if(hasTakeOut){
                    log.debug("可以开始干活了");
                }else {
                    log.debug("饭都不吃，搞个几把");
                }
            }
        },"玄子").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (room){
                    log.debug("可以开始干活了");
                }
            },"其他人"+i).start();
        }

        sleep(1);
//        new Thread(()->{
//            synchronized (room){
//                hasCigaratte = true;
//                log.debug("烟到了");
//                room.notify();
//            }
//        },"阿咪").start();
        new Thread(()->{
            synchronized (room){
                hasTakeOut = true;
                log.debug("外卖到咯");
//                room.notify();
                room.notifyAll();
            }
        },"外卖小哥").start();

    }
}
