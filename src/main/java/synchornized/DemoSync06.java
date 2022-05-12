package synchornized;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
* 线程安全习题1
* */
public class DemoSync06{
    //是否安全? 否，hashtable是线程安全的，hashamap不是
    Map<String,Object> map = new HashMap<>();

    //是否安全？ 安全，String不可变类是线程安全的
    String s1 = "...";
    //是否安全？ 安全，String不可变类是线程安全的
    final String s2 = "...";
    //是否安全？ 不安全，Date不是安全类
    Date d1 = new Date();

    //是否安全？不安全，final只是保证了的d2的引用值固定，不代表Date不可变
    final Date d2 = new Date();
}
