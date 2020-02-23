package ru.gb.jtwo.leight.online;

public class Main {
    public static void main(String[] args) {
        /*
2)Class.forName("org.sqlite.JDBC"); - что делает эта строка?
статическая конструкция static {....}
        * */
        try {
            Class c = Class.forName("ru.gb.jtwo.leight.online.MyClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println("Hello " + "world");
//        String str = "world";
//        System.out.printf("Hello %s\n", str);
    }
}
