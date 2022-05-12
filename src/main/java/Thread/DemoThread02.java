package Thread;


import lombok.extern.slf4j.Slf4j;

/*
* 创建线程
* 方法2
*   Runnable
* */
@Slf4j(topic = "c.Test2")
public class DemoThread02 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        //java 8 lamda简化
        Runnable r2 = ()->{log.debug("running");};


        Thread t = new Thread(runnable,"t2");


        t.start();


    }
}
