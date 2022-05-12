package wait;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static utils.Slepper.sleep;

/*
* Guard Suspension模式
* */
@Slf4j(topic = "c.wait09")
public class DemoWait09 {

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

class Gurad{
    //结果
    private Object response;

    //获取结果
    public Object get(){
        synchronized (this){
            //没有结果
            while(response == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
