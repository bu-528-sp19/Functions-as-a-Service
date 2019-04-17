import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {


        /*DriverThread[] dt = new DriverThread[5];
        PassengerThread[] pt = new PassengerThread[5];

        for (DriverThread driver : dt) {
            driver = new DriverThread(10);
            driver.start();
        }

        for (PassengerThread passenger : pt) {
            passenger = new PassengerThread(10);
            passenger.start();
        }*/

        ExecutorService pool = Executors.newFixedThreadPool(100);
        //pool.submit(new PassengerRunnable(10, 5));
        for (int i = 0; i < 50; i++) {
            pool.submit(new DriverRunnable(50, 10));
        }

        for (int i = 0; i < 50; i++) {
            pool.submit(new PassengerRunnable(50, 10));
        }
    }
}




