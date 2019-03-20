public class Main {

    public static void main(String[] args) {

        DriverThread driverThread = new DriverThread(100);
        PassengerThread passengerThread = new PassengerThread(100);

        driverThread.start();
        passengerThread.start();


    }


}


