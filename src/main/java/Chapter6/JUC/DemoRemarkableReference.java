package Chapter6.JUC;

import lombok.extern.slf4j.Slf4j;
import utils.Slepper;

import java.util.concurrent.atomic.AtomicMarkableReference;

@Slf4j(topic = "c.remarkable")
public class DemoRemarkableReference {
    public static void main(String[] args) {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag,true);

        log.debug("start");
        GarbageBag prev = ref.getReference();
        log.debug(prev.toString());

        new Thread(()->{
            log.debug("start..");
            bag.setDesc("空垃圾袋");
            ref.compareAndSet(bag,bag,true,false);
            log.debug(bag.toString());
        },"保洁阿姨").start();


        Slepper.sleep(1);
        log.debug("想换一只新的垃圾袋?");
        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.debug("换了么?"+success);
        log.debug(ref.getReference().toString());
    }
}

class GarbageBag{
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GarbageBag{" +
                "desc='" + desc + '\'' +
                '}';
    }
}