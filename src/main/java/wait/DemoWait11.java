package wait;

import lombok.extern.slf4j.Slf4j;

import java.security.GuardedObject;
import java.util.*;

import static utils.Slepper.sleep;

/*
* Guard Suspension模式增强
* */
@Slf4j(topic = "c.wait11")
public class DemoWait11 {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Peopel().start();
        }
        sleep(1);
        for (Integer id:MailBoxes.getIds()) {
            new Postman(id,"内容"+id).start();
        }
    }
}

@Slf4j(topic = "c.People")
class Peopel extends Thread{
    @Override
    public void run() {
        //收信
        GuradS2 guardedObject = MailBoxes.createGuardedObject();
        log.debug("开始收信id:{}",guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id:{}.内容:{}",guardedObject.getId(),mail);
    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread{
    private int id;
    private String mail;

    public Postman(int id,String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        //送信
        GuradS2 guardObject = MailBoxes.getGuardObject(id);
        log.debug("开始送信 id:{}.内容:{}",guardObject.getId(),mail);
        guardObject.compelete(mail);
    }
}

class MailBoxes{
    private static Map<Integer, GuradS2> boxes = new Hashtable<>();

    public static int id = 1;
    //产生唯一id
    public static synchronized int generateId(){
        return id++;
    }
    //产生guard对象
    public static GuradS2 createGuardedObject(){
        GuradS2 guradS2 = new GuradS2(generateId());
        boxes.put(guradS2.getId(),guradS2);
        return guradS2;
    }

    public static GuradS2 getGuardObject(int id){
        return boxes.remove(id);
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }
}

class GuradS2{

    private int id;

    public GuradS2(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

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
