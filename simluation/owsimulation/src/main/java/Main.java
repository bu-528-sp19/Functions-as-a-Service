import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static volatile AtomicInteger totalTimes = new AtomicInteger(0);


    public ScheduledExecutorService createHelperRunnable() {
        ScheduledExecutorService helperPool = Executors.newSingleThreadScheduledExecutor();
        helperPool.scheduleAtFixedRate(new RequestHelperRunnable(), 0, 1, TimeUnit.SECONDS );
        return helperPool;
    }

    public ScheduledExecutorService createDriversRunnalble(int num) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(num);
        for (int i = 0; i < num; i++) {
            if (i < 10) {
                DriverScheduleRunnable driverScheduleRunnable = new DriverScheduleRunnable(5, i+"");
                pool.scheduleAtFixedRate(driverScheduleRunnable, 0, 2, TimeUnit.SECONDS );
            } else {
                DriverScheduleRunnable driverScheduleRunnable = new DriverScheduleRunnable(5, i+"");
                pool.scheduleAtFixedRate(driverScheduleRunnable, 60, 2, TimeUnit.SECONDS );
            }

        }
        return pool;
    }

    public ScheduledExecutorService createPassengersRunnalble(int num) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(num);
        for (int i = 0; i < num; i++) {
            if (i < 2) {
                PassengerScheduleRunnable passengerScheduleRunnable = new PassengerScheduleRunnable(10, i+"");
                pool.scheduleAtFixedRate(passengerScheduleRunnable, 0, 2, TimeUnit.SECONDS );
            } else {
                PassengerScheduleRunnable passengerScheduleRunnable = new PassengerScheduleRunnable(5, i+"");
                pool.scheduleAtFixedRate(passengerScheduleRunnable, 20, 2, TimeUnit.SECONDS );
            }

        }
        return pool;
    }

    public void shutDownPool(ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.shutdown();
        System.out.println("awaitTermination...");
        boolean isDone;
            // 等待线程池终止
        try{
            do {
                isDone = scheduledExecutorService.awaitTermination(1, TimeUnit.DAYS);
            } while(!isDone);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("ShutDown finished");
    }

//    public ScheduledExecutorService createPassengersRunnable(int num) {
//        ScheduledExecutorService pool = Executors.newScheduledThreadPool(num);
//        for (int i = 0; i < num; i++) {
//            PassengerScheduleRunnable passengerScheduleRunnable = new PassengerScheduleRunnable(10, i+"");
//            pool.scheduleAtFixedRate(passengerScheduleRunnable, i, 2, TimeUnit.SECONDS );
//        }
//        return pool;
//    }



    public static void main(String[] args) {

        Main main = new Main();

        //ScheduledExecutorService helperPool = main.createHelperRunnable();
        ScheduledExecutorService driversPool = main.createDriversRunnalble(100);
        //ScheduledExecutorService passengersPool = main.createPassengersRunnalble(50);

//        try {
//            Thread.sleep(10000);
//            main.shutDownPool(driversPool);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
//        try {
//            Thread.sleep(20000);
//            System.out.println("Shutting down executor...");
//            // 关闭线程池
//            pool.shutdown();
//            boolean isDone;
//            // 等待线程池终止
//            do {
//                isDone = pool.awaitTermination(1, TimeUnit.DAYS);
//                System.out.println("awaitTermination...");
//            } while(!isDone);
//            System.out.println("Finished all threads");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}




