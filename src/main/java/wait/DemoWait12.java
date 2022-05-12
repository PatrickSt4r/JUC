package wait;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

import static utils.Slepper.sleep;

/*
* 消费者/生产者
* 线程中通信
* */
@Slf4j(topic = "c.wait12")
public class DemoWait12 {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(()->{
                queue.put(new Message(id,"值"+id));
            },"生产者"+i).start();
        }
        new Thread(()->{
            while(true){
                sleep(1);
                Message take = queue.take();
            }
        },"消费者").start();

    }
}

//消息队列类，java线程之间通信
@Slf4j(topic = "c.MessageQueue")
class MessageQueue{
    //消息队列集合
    private LinkedList<Message> list = new LinkedList<>();
    //队列容量
    private int capcity;


    public MessageQueue(int capticy){
        this.capcity = capticy;
    }

    //获取消息
    public Message take(){
        //检查队列是否为空
        synchronized (list){
            while(list.isEmpty()){
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //从队列的头部获取消息返回
            Message message = list.removeFirst();
            log.debug("已消费消息{}",message);
            list.notifyAll();
            return message;
        }
    }

    //存入消息
    public void put(Message message){
        synchronized (list){
            //检查队列是否已满
            while(list.size()==capcity){
                try {
                    log.debug("队列已满，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            log.debug("已生产消息:{}",message);
            list.notifyAll();
        }
    }
}

final class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}