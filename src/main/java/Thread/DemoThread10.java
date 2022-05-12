package Thread;

import lombok.extern.slf4j.Slf4j;

/*
* 线程优先级演示
* */
@Slf4j(topic = "c.test")
public class DemoThread10 {
    public static void main(String[] args) {
        Runnable r1 = ()->{
            int count = 0;
            for(;;){
                System.out.println("---->1 "+count++);
            }
        };

        Runnable r2 = ()->{
            int count = 0;
            for(;;){
//                Thread.yield();
                System.out.println("            ---->2 "+count++);
            }
        };
        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");
//        t1.setPriority(Thread.MIN_PRIORITY);
//        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();


    }
}
