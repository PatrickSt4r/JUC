package wait;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/*
* Guard Suspension模式增强
* */
@Slf4j(topic = "c.wait10")
public class DemoWait10 {

    public static void main(String[] args) {
        Gurad gurad = new Gurad();
        new Thread(()->{
            log.debug("等待结果");
            List<Integer> res = (List<Integer>) gurad.get();
            log.debug("长度:{}",res.size());
        },"t1").start();
        new Thread(()->{
            List list = new ArrayList<>();
            for (int i = 0; i < 10000000; i++) {
                list.add(i);
            }
            gurad.compelete(list);
        },"t2").start();
    }
}

class GuradS1{
    //结果
    private Object response;

    //获取结果
    //timeout表示要等待多久
    public Object get(long timeout){
        synchronized (this){
            //记录一下开始时间
            long begin = System.currentTimeMillis();
            //经历的时间
            long passTime = 0;
            while(response == null){
                //这一轮循环应该等待的时间
                long waitTime = timeout - passTime;
                //经历时间超过了最大等待时间时，退出循环
                if(waitTime<=0){
                    break;
                }
                try {
                    this.wait(waitTime); //虚假唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //经历时间
                passTime = System.currentTimeMillis() - begin;
            }
        }
        return response;
    }
    //产生结果
    public void compelete(Object response){
        synchronized (this){
            //给成员变量赋值
            this.response =response;
            this.notifyAll();
        }
    }
}
