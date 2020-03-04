package lesson05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CountDownLatch cdlOnStart;
    private CountDownLatch cdlOnFinish;
    private CountDownLatch cdlOnWinner;
    private CyclicBarrier cb;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch cdlOnStart, CyclicBarrier cb, CountDownLatch cdlOnFinish, CountDownLatch cdlOnWinner) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cdlOnStart = cdlOnStart;
        this.cb = cb;
        this.cdlOnFinish = cdlOnFinish;
        this.cdlOnWinner = cdlOnWinner;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            cdlOnStart.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (cdlOnWinner.getCount() == 1){
            cdlOnWinner.countDown();
            System.out.println(this.name + " - WINS");
        }
        cdlOnFinish.countDown();
    }
}