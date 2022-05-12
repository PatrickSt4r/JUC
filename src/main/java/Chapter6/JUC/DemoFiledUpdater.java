package Chapter6.JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/*
* 原子更新器
* */
@Slf4j(topic = "c.fieldUpdater")
public class DemoFiledUpdater {
    public static void main(String[] args) {
        Student stu = new Student();


        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");

        boolean flag = updater.compareAndSet(stu, null, "张三");
        System.out.println(flag);
        System.out.println(stu);

    }
}

class Student{

    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}