package Chapter6.JUC;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/*
* ABA问题
*
* */
@Slf4j(topic = "c.ABA")
public class DemoABA {
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);
    public static void main(String[] args) {
        log.debug("main start ...");
        //获取值
        //这个共享变量被其他线程修改过?
        String prev = ref.getReference();
        //获取版本号
        int stamp = ref.getStamp();
        log.debug("{}",stamp);
        other();
        Slepper.sleep(1);
        log.debug("{}",stamp);
        //尝试改为C
        log.debug("change A->C {}",ref.compareAndSet(prev,"C",stamp,stamp+1));
    }


    private static void other(){
        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("{}",stamp);
            log.debug("change A->B {}",ref.compareAndSet(ref.getReference(),"B",stamp,stamp+1));
        },"t1").start();
        Slepper.sleep(0.5);
        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("{}",stamp);
            log.debug("change B->A {}",ref.compareAndSet(ref.getReference(),"A",stamp,stamp+1));
        },"t2").start();
    }
}
