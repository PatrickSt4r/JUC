package Chapter6.JUC;

import sun.misc.Unsafe;
import utils.UnsafeAccessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DemoAtomicAccount {
    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(10000));
    }
}

class MyAtomicInteger implements Account{
    private volatile int value;
    private static final long valueOffset;
    static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void decrement(int amount){
        while (true){
            int prev = this.value;
            int next = prev - amount;
            if(UNSAFE.compareAndSwapInt(this,valueOffset,prev,next)){
                break;
            }
        }
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }

}

interface Account{
    //获取余额
    Integer getBalance();

    //取款
    void withdraw(Integer amount);

    static void demo(Account account){
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}
