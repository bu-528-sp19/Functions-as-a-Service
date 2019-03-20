public class Main {

    public static void main(String[] args) {

        DriverThread driverThread = new DriverThread(1);
        PassengerThread passengerThread = new PassengerThread(1);

        driverThread.start();
        passengerThread.start();


    }


}


