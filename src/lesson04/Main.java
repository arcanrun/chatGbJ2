package lesson04;

public class Main {
    static private volatile String currentLetter = "A";
    static private final Object monitor = new Object();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        synchronized (monitor) {
                            while (!currentLetter.equals("A")) {
                                monitor.wait();
                            }
                            System.out.print("A");
                            currentLetter = "B";
                            monitor.notifyAll();
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "Thread A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i = 0; i < 5; i++) {
                        synchronized (monitor) {
                            while (!currentLetter.equals("B")) {
                                monitor.wait();
                            }
                            System.out.print("B");
                            currentLetter = "C";
                            monitor.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "Thread B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        synchronized (monitor) {
                            while (!currentLetter.equals("C")) {
                                monitor.wait();
                            }
                            System.out.print("C");
                            currentLetter = "A";
                            monitor.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "Thread C").start();
    }
}
