public class Main {

    public static void main(String[] args) {


        DriverThread[] dt = new DriverThread[5];
        PassengerThread[] pt = new PassengerThread[5];

        for (DriverThread driver : dt) {
            driver = new DriverThread(10);
            driver.start();
        }

        for (PassengerThread passenger : pt) {
            passenger = new PassengerThread(10);
            passenger.start();
        }


    }


}


