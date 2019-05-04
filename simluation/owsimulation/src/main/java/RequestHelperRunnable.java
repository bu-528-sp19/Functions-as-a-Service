import java.util.concurrent.atomic.AtomicInteger;

public class RequestHelperRunnable implements Runnable{

    private int prevCal = 0;
    private int variation = 0;

    @Override
    public void run() {
        int currentVariation = Main.totalTimes.get()-prevCal;
        System.out.println("Numbers of requests per second: " + currentVariation);
        System.out.println("variation comparing with last second: " + (currentVariation - variation));
        System.out.println("Total requests sent: " + Main.totalTimes.get());
        prevCal = Main.totalTimes.get();
        variation = currentVariation;
    }
}
