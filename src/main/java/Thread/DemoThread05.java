package Thread;

/*
* 线程运行原理-多线程
* 相互独立，互不干扰
* */
public class DemoThread05 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            method1(20);
        });

        thread.setName("t1");
        thread.start();
        method1(10);
    }

    private static void method1(int x){
        int y = x+1;
        Object m = mehthod2();
    }

    private static Object mehthod2(){
        Object m = new Object();
        return m;
    }

}
