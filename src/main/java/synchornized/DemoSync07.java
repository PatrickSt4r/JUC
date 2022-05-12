package synchornized;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static utils.Slepper.sleep;


/*
* 售票买票
* */
@Slf4j(topic = "c.SellTickets")
public class DemoSync07 {

    public static void main(String[] args) throws InterruptedException {

        //模拟多人买票
        TickWindow tickWindow = new TickWindow(10000);
        //卖出的票数统计,这里用vector，因为vector是线程安全的
        List<Integer> sellList = new Vector<>();
        //所有线程的集合
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            Thread singleThread = new Thread(() -> {
                //卖票
                int sell = tickWindow.sell(RandomAmount());
                sellList.add(sell);
            });
            threadList.add(singleThread);
            singleThread.start();

        }
        for (Thread t:threadList) {
            t.join();
        }
        //余票数量
        int count = tickWindow.getCount();
        int sum = sellList.stream().mapToInt(Integer::intValue).sum();
        log.debug("余票:{}",count);
        log.debug("卖出去的票数:{}",sum);
    }

    static Random random = new Random();
    public static int RandomAmount(){
        return random.nextInt(5)+1;
    }

}

class TickWindow{
    private int count;

    public TickWindow(int count){
        this.count = count;
    }

    //获取余票
    public int getCount() {
        return count;
    }

    //卖票,
    //解决方法，给数据读写的临界区增加synchronized关键字
    public synchronized int sell(int amount){
        if(count >= amount){
            this.count -= amount;
            return amount;
        }else{
            return 0;
        }
    }
}
