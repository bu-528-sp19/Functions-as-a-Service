import redis.clients.jedis.Jedis;

import java.util.Set;

public class ThreadRoamingActivity extends Thread{

    Jedis driverInfoConnection = RedisHelper.getDriverInfoConnection();
    Jedis passengerInfoConnection = RedisHelper.getPassengerInfoConnection();
    final RedisHelper redisHelper;

    public ThreadRoamingActivity(RedisHelper redisHelper) {
        super();
        this.redisHelper = redisHelper;
    }

    @Override
    public void run() {
        synchronized (redisHelper) {
            ;
        }


        while(true) {
            synchronized (redisHelper) {
                try {
                    Set<String> drivers = driverInfoConnection.keys("*");
                    Set<String> passengers = passengerInfoConnection.keys("*");

                    for (String s : passengers) {
                        System.out.println(s + "location: " + passengerInfoConnection.hget(s, "current_location"));
                    }

                    for (String s : drivers) {
                        System.out.println(s + "location: " + driverInfoConnection.hget(s, "current_location"));
                    }
                    sleep(3000);
                    System.out.println("server sleep");
                    redisHelper.notifyAll();
                    redisHelper.wait();
                } catch (Exception e) {
                    ;
                }
            }
        }

    }

}
