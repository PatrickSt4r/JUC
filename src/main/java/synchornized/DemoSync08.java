package synchornized;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/*
* 转账练习
* */
@Slf4j(topic = "c.trasfer")
public class DemoSync08 {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(2000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                a.transfer(b,RandomMoney());
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, RandomMoney());
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

//        log.debug("a剩余金额:{}",a.getMoney());
//        log.debug("b剩余金额:{}",b.getMoney());

    }



    static Random random = new Random();

    public static int RandomMoney(){
        return random.nextInt(100)+1;
    }
}

class Account{
    private int money;

    public Account(int money){
        this.money = money;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }

    //转账
    //解决办法 加在Account类上，target和自己都是共享Account类
    public void transfer(Account target,int amount){
        synchronized (Account.class){
            if(this.money >= amount){
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }

}
