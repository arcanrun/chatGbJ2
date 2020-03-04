package lesson05;

import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final CountDownLatch cdlStart = new CountDownLatch(CARS_COUNT);
    public static final CountDownLatch cdlFinish = new CountDownLatch(CARS_COUNT);
    public static final CyclicBarrier cbOnAllReady = new CyclicBarrier(CARS_COUNT);
    public static final CountDownLatch cdlOnWinner = new CountDownLatch(1);
    public static final CyclicBarrier cbOnTunnel = new CyclicBarrier(CARS_COUNT /2);


    public static void main(String[] args) {


        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Race race = new Race(new Road(60), new Tunnel(cbOnTunnel), new Road(40));

        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cdlStart, cbOnAllReady,cdlFinish, cdlOnWinner);
        }


        for (Car car : cars) {
            new Thread(car).start();
        }
        try {

            cdlStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try{
            cdlOnWinner.await();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        try {
            cdlFinish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}




