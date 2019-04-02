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

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new PassengerRunnable(100, 5));
        pool.submit(new DriverRunnable(100, 5));
    }
}

class DriverRunnable implements Runnable {
    int maximum;
    int increment;
    ArrayList<Driver> drivers;


    public DriverRunnable (int maximum, int increment) {
        this.maximum = maximum;
        this.increment = increment;
        this.drivers = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(drivers.size());
            if (drivers.size() < maximum) {
                drivers.add(DataLayerHelper.createDraftDriver());
            }
            for (Driver driver : drivers) {
                DataLayerHelper.randomMoveDriver(driver);
                PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, driver.getId());
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


class PassengerRunnable implements Runnable {
    int maximum;
    int increment;
    ArrayList<Passenger> passengers;


    public PassengerRunnable (int maximum, int increment) {
        this.maximum = maximum;
        this.increment = increment;
        this.passengers = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(passengers.size());
            if (passengers.size() < maximum) {
                passengers.add(DataLayerHelper.createDraftPassenger());
            }
            for (Passenger passenger: passengers) {
                DataLayerHelper.randomMovePassenger(passenger);
                PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, passenger.getId());
                System.out.println(DataLayerHelper.wrapPassengerJson(passenger));
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


