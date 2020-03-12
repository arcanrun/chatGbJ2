package lesson07;

public class Main {
    public static void main(String[] args) {
        Invoker invoker = new Invoker(new SomeClass());
        invoker.start();
    }
}
