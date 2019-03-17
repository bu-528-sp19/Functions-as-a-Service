import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataLayerHelper {

    //all candidate chars that would be using in generating
    private static final String[] CANDIDATE_CHARS =  new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
    private double[] noLocation = {-1,-1};

    // usually a person can move 0.9m per second, equals to 8.4e-6 latitude, equals to 8.4e-6*cos(latitude) longitude
    // in this simulation, driver speed would be 6m/s, equals to 5.4e-5 lati/s, equals to 5.4e-5*cos(latitude) longitude/s
    // basic simulation: passenger 42.3508011,-71.1399532 -> driver 42.3493101,-71.1075554->destination:42.3515459,-71.0664048

    public void packDP(Driver driver, Passenger passenger) {
        // add driver to passenger
        passenger.setAssignedDriver(driver);
        passenger.setState(2);

        // add passenger to driver
        driver.setAssignedPassenger(passenger);
        driver.setDestinationLocation(passenger.getDestinationLocation());
        driver.setPassengerLocation(passenger.getCurrentLocation());
        driver.setState(2);
    }

    public void driver2Idle(Driver driver) {
        driver.setAssignedPassenger(null);
        driver.setDestinationLocation(noLocation);
        driver.setPassengerLocation(noLocation);
        driver.setState(1);
    }


    public void driverMove(Driver driver) {
        if (driver.getState() == 1 || driver.getState() == 4) {
            return;
        }

        double[] currentLocation = driver.getCurrentLocation();
        double[] selfPara = driver.getMovePara();

        // need to modify when we add action to state 1 and state 4
        double[] currentDestination = (driver.getState() == 2) ? driver.getPassengerLocation() : driver.getDestinationLocation();

        if (currentDestination[0] != currentLocation[0]) {
            if (Math.abs(currentLocation[0] - currentDestination[0]) < selfPara[0]) {
                currentLocation[0] = currentDestination[0];
            } else {
                currentLocation[0] += ((currentLocation[0] > currentDestination[0]) ? -1 : 1) * selfPara[0];
            }
        } else if (currentDestination[1] != currentLocation[1]) {
            if (Math.abs(currentLocation[1] - currentDestination[1]) < selfPara[1]) {
                currentLocation[1] = currentDestination[1];
            } else {
                currentLocation[1] += ((currentLocation[1] > currentDestination[1]) ? -1 : 1) * selfPara[1];
            }
        } else {
            driver.setState((driver.getState() == 2) ? 3 : 4);
        }
    }

    public void passengerMove(Passenger passenger) {
        if (passenger.getState() == 2 || passenger.getState() == 3) {
            try {
                Driver driver = passenger.getAssignedDriver();
                passenger.setCurrentLocation(driver.getCurrentLocation());
                passenger.setState(driver.getState());
            } catch (Exception e) {
                System.out.println("No driver assigned");
            }
        }
    }

    public void passengerToIdle(Passenger passenger) {
        passenger.setAssignedDriver(null);
        Passenger.getPassengerIDs().remove(passenger.getId());
        Passenger.getPassengerList().remove(passenger);
    }

    public static String generateDriverID() {
        String resultID = DataLayerHelper.generateID('D');
        while (Driver.getDriverIDs().contains(resultID)) {
            resultID = DataLayerHelper.generateID('D');
        }
        return resultID;
    }

    public static String generatePassengerID() {
        String resultID = DataLayerHelper.generateID('D');
        while (Driver.getDriverIDs().contains(resultID)) {
            resultID = DataLayerHelper.generateID('D');
        }
        return resultID;
    }

    public static Driver createNewDriver() {
        String driverID = generateDriverID();
        //double[] location = {42.3508011,-71.1399532};
        double[] selfLocation = {42.3508011,-71.1399532};
        double[] pasLocation = {-1,-1};
        double[] destiLocation = {-1,-1};
        double[] movePara = {5.4e-5, 5.4e-5 * Math.cos(selfLocation[0])};
        Driver driver = new Driver(driverID, selfLocation, 0, movePara, pasLocation, destiLocation, timeStamp(), null);
        Driver.getDriverList().add(driver);
        Driver.getDriverIDs().add(driverID);
        return driver;
    }

    private static String timeStamp() {
        String FORMAT_FULL = "yyyy MM dd HH mm ss S";
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime()).toString();
    }

    private static String generateID(char type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        for (int i = 0; i < 15; i++) {
            sb.append(CANDIDATE_CHARS[(int)(Math.random()*(CANDIDATE_CHARS.length-1))]);
        }
        return sb.toString();
    }


}
