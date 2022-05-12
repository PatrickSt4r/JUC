package Chapter6.CAS;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
* 加锁实现取款
* */
@Slf4j(topic = "c.cas1")
public class DemoCAS01 {
    public static void main(String[] args) {
        Account accountU = new AccountUnsafe(10000);
        Account.demo(accountU);
        Account account = new AccountCas(10000);
        Account.demo(account);
    }

}

class AccountCas implements Account{

    private AtomicInteger blance;

    public AccountCas(int blance) {
        this.blance = new AtomicInteger(blance);
    }

    @Override
    public Integer getBalance() {
        return blance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true){
            //获取余额最新值
            int previous = blance.get();
            //要修改的余额
            int next = previous - amount;
            //真正修改 next同步到主存
            if(blance.compareAndSet(previous,next)){
                break;
            }
        }
    }
}

class AccountUnsafe implements Account{

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        synchronized (this){
            this.balance = balance;
        }
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(Integer amount) {
        synchronized (this){
            this.balance -= amount;
        }
    }
}

interface Account{
    //获取余额
    Integer getBalance();

    //取款
    void withdraw(Integer amount);

    /*
    * 方法内启动1000个线程，每个线程做 -10块 的操作
    * 如果初始余额为10000 那么正确结果应该是0
    * */

    static void demo(Account account){
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(10);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost:" + (end-start)/1000_000 + "ms");
    }
}
