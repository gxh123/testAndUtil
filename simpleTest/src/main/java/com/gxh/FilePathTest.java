package com.gxh;

import java.net.URL;

public class FilePathTest {

    public static void main(String[] args) {
        URL path = FilePathTest.class.getResource("");
        System.out.println(path.toString());
        //得到的是当前类FileTest.class文件的URI目录。不包括自己！
        //打印：file:/Users/gxh/IdeaProjects2/testAndUtil/simpleTest/target/classes/com/gxh/

        path = FilePathTest.class.getResource("/");
        System.out.println(path.toString());
        //得到的是当前的classpath的绝对URI路径。
        //打印：file:/Users/gxh/IdeaProjects2/testAndUtil/simpleTest/target/classes/

        path = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(path.toString());
        //得到的也是当前ClassPath的绝对URI路径。
        //打印：file:/Users/gxh/IdeaProjects2/testAndUtil/simpleTest/target/classes/

        path = FilePathTest.class.getClassLoader().getResource("");
        System.out.println(path.toString());
        //得到的也是当前ClassPath的绝对URI路径。
        //打印：file:/Users/gxh/IdeaProjects2/testAndUtil/simpleTest/target/classes/

        path = ClassLoader.getSystemResource("");
        System.out.println(path.toString());
        //得到的也是当前ClassPath的绝对URI路径。
        //打印：file:/Users/gxh/IdeaProjects2/testAndUtil/simpleTest/target/classes/

    }


}
