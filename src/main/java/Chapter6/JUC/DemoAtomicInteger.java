package Chapter6.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Slf4j(topic = "c.atomic01")
public class DemoAtomicInteger {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(2);

//        System.out.println(i.incrementAndGet());// ++i 1
//        System.out.println(i.getAndIncrement());// i++ 2
//
//        i.decrementAndGet(); //--i 1
//        i.getAndDecrement(); //i-- 0
//
//        i.getAndAdd(5); //先获取再增加 5
//        i.addAndGet(5);//先加后获取 10
        //            读取到   设置值
//        i.updateAndGet(x -> x * 10);


        System.out.println(updateAndGet(i , p->p*5));



//        System.out.println(i.get());
    }

    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator){
        while (true){
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if(i.compareAndSet(prev,next)){
                return i.get();
            }
        }
    }
}
