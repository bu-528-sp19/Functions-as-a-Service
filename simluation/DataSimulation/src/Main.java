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

        ExecutorService pool = Executors.newCachedThreadPool();
        //pool.submit(new PassengerRunnable(10, 5));
        pool.submit(new DriverRunnable(10, 2));
    }
}




