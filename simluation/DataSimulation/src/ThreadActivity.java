import redis.clients.jedis.Jedis;

import java.util.Set;

public class ThreadActivity {

    public synchronized void userActivity() {
        Driver driver = DataLayerHelper.createNewDriver();
        Passenger passenger = DataLayerHelper.createNewPassenger();
        try {
            while (true) {
                Thread.sleep(1000);
                RedisHelper.request4Driver(driver);
                if (passenger.getState() != 4) RedisHelper.request4Passenger(passenger);
                notifyAll();
                wait();
            }
        } catch (InterruptedException ie) {
            System.out.println("goujiao");
        }
    }



    public synchronized void serverActivity() {
        Jedis driverInfoConnection = RedisHelper.getDriverInfoConnection();
        Jedis driverListConnection = RedisHelper.getDriverListConnection();
        Jedis passengerInfoConnection = RedisHelper.getPassengerInfoConnection();
        Jedis passengerListConnection = RedisHelper.getPassengerListConnection();
        try {
            while (true) {
                Thread.sleep(1000);
                String candiDriver = driverListConnection.spop("list");
                String candiPassenger = passengerListConnection.spop("list");

                if (candiDriver != null && candiPassenger != null) {
                    DataLayerHelper.packDP(DataLayerHelper.searchDriver(candiDriver), DataLayerHelper.searchPassenger(candiPassenger));
                    System.out.println("Matching: " + candiDriver + " with " + candiPassenger);
                    continue;
                }

                // need checking validation
               /* if (!candiDriver.equals("nil") && !candiPassenger.equals("nil")) {

                }*/

                Set<String> drivers = driverInfoConnection.keys("*");
                Set<String> passengers = passengerInfoConnection.keys("*");

                for (String s: passengers) {
                    Passenger passenger = DataLayerHelper.searchPassenger(s);
                    System.out.println(s + ": State "+ passengerInfoConnection.hget(s, "state") + "location: " + passengerInfoConnection.hget(s, "current_location"));
                    DataLayerHelper.passengerMove(passenger);
                }

                for (String s : drivers) {
                    Driver driver = DataLayerHelper.searchDriver(s);
                    System.out.println(s + ": State "+ driverInfoConnection.hget(s,"state") + "location: " + driverInfoConnection.hget(s, "current_location"));
                    DataLayerHelper.driverMove(driver);
                    if (driverInfoConnection.hget(s,"state").equals("4")) {
                        DataLayerHelper.driver2Idle(driver);
                    }
                }


                notifyAll();
                wait();
            }
        } catch (InterruptedException ie) {
            System.out.println("goujiao");
        }
    }

}
