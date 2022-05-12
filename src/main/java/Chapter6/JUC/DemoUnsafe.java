package Chapter6.JUC;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/*
* unsafe
* */
public class DemoUnsafe {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);

        //1 获取域的偏移地址
        long idoffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameoffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();
        //2.执行CAS操作
        unsafe.compareAndSwapInt(t,idoffset,0,1);
        unsafe.compareAndSwapObject(t,nameoffset,null,"张三");

        //3.验证结果
        System.out.println(t);
    }
}

@Data
class Teacher{
    volatile int id;
    volatile String name;
}
