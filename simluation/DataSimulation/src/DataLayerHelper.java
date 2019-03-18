import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DataLayerHelper {

    //all candidate chars that would be using in generating
    private static final String[] CANDIDATE_CHARS =  new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
    private static final double[] NULL_LOCATION = {-1,-1};
    private static final double[] BOTTOMLEFT = {42.3259015,-71.0797181};
    private static final double[] TOPRIGHT = {42.3863267,-71.0234668};

    // usually a person can move 0.9m per second, equals to 8.4e-6 latitude, equals to 8.4e-6*cos(latitude) longitude
    // in this simulation, driver speed would be 11m/s, equals to 1e-4 lati/s, equals to 1e-4/cos(latitude) longitude/s
    // basic simulation: passenger 42.3508011,-71.1399532 -> driver 42.3493101,-71.1075554->destination:42.3515459,-71.0664048

    public static void randomMoveDriver(Driver driver) {

        double[] currentLocation = driver.getCurrentLocation();
        double[] movePara = driver.getMovePara();
        generateMoveLocation(currentLocation, movePara);
    }

    public static void randomMovePassenger(Passenger passenger) {
        double[] currentLocation = passenger.getCurrentLocation();
        double[] movePara = passenger.getMovePara();
        generateMoveLocation(currentLocation, movePara);
    }


    public static void packDP(Driver driver, Passenger passenger) {
        // add driver to passenger
        System.out.println((driver == null) + "" + passenger == null);
        passenger.setAssignedDriver(driver);
        passenger.setState(2);

        // add passenger to driver
        driver.setAssignedPassenger(passenger);
        driver.setDestinationLocation(passenger.getDestinationLocation());
        driver.setPassengerLocation(passenger.getCurrentLocation());
        driver.setState(2);
    }

    public static void driver2Idle(Driver driver) {
        passenger2Idle(driver.getAssignedPassenger());
        driver.setAssignedPassenger(null);
        driver.setDestinationLocation(NULL_LOCATION);
        driver.setPassengerLocation(NULL_LOCATION);
        driver.setState(1);
    }

    private static void passenger2Idle(Passenger passenger) {
        passenger.setAssignedDriver(null);
        Passenger.passengerIDs.remove(passenger.getId());
        Passenger.passengerList.remove(passenger);
        RedisHelper.deletePassenger(passenger.getId());
    }

    public static boolean driverMove(Driver driver) {
        if (driver == null || driver.getState() == 1 || driver.getState() == 4) {
            return false;
        }

        double[] currentLocation = driver.getCurrentLocation();
        double[] selfPara = driver.getMovePara();

        // need to modify when we add action to state 1 and state 4
        double[] currentDestination = (driver.getState() == 2) ? driver.getPassengerLocation() : driver.getDestinationLocation();
        if (currentDestination[0] != currentLocation[0]) {
            //System.out.println(currentDestination[0] + " " + currentLocation[0]);
            if (Math.abs(currentLocation[0] - currentDestination[0]) < selfPara[0]) {
                currentLocation[0] = currentDestination[0];
            } else {
                currentLocation[0] += ((currentLocation[0] > currentDestination[0]) ? -1 : 1) * selfPara[0];
            }
        } else if (currentDestination[1] != currentLocation[1]) {
            //System.out.println(currentDestination[1] + " " + currentLocation[1] + " " + selfPara[1]);
            if (Math.abs(currentLocation[1] - currentDestination[1]) < selfPara[1]) {
                currentLocation[1] = currentDestination[1];
            } else {
                currentLocation[1] += ((currentLocation[1] > currentDestination[1]) ? -1 : 1) * selfPara[1];
            }
        } else {
            driver.setState((driver.getState() == 2) ? 3 : 4);
            driver.getAssignedPassenger().setState(driver.getState());
            System.out.println("set to " + driver.getState());
        }
        return true;
    }

    public static void passengerMove(Passenger passenger) {
        if (passenger.getState() == 3) {
            try {
                Driver driver = passenger.getAssignedDriver();
                passenger.setCurrentLocation(driver.getCurrentLocation());
                passenger.setState(driver.getState());
            } catch (Exception e) {
                System.out.println("No driver assigned");
            }
        }
    }



    public static String generateDriverID() {
        String resultID = DataLayerHelper.generateID('D');
        while (Driver.driverIDs.contains(resultID)) {
            resultID = DataLayerHelper.generateID('D');
        }
        return resultID;
    }

    public static String generatePassengerID() {
        String resultID = DataLayerHelper.generateID('P');
        while (Passenger.passengerIDs.contains(resultID)) {
            resultID = DataLayerHelper.generateID('P');
        }
        return resultID;
    }

    public static Driver searchDriver(String id) {
        for (Driver driver : Driver.driverList) {
            if (driver.getId().equals(id)) return driver;
        }
        return null;
    }

    public static Passenger searchPassenger(String id) {
        for (Passenger passenger : Passenger.passengerList) {
            if (passenger.getId().equals(id)) return passenger;
        }
        return null;
    }

    public static Driver createDraftDriver() {
        String driverID = generateDriverID();
        //double[] location = {42.3508011,-71.1399532};
        double[] selfLocation = generateRandomLocation();
        double[] movePara = {1e-4, Math.abs(1e-4 / Math.cos(selfLocation[0]))};
        Driver driver = new Driver(driverID, selfLocation, movePara);
        Driver.getDriverList().add(driver);
        Driver.getDriverIDs().add(driverID);
        RedisHelper.addNewDriver(driverID);
        return driver;
    }

    public static Passenger createDraftPassenger() {
        String passengerID = generatePassengerID();
        //double[] location = {42.3508011,-71.1399532};
        double[] selfLocation = generateRandomLocation();
        double[] movePara = {8.4e-6, Math.abs(8.4e-6 / Math.cos(selfLocation[0]))};
        Passenger passenger = new Passenger(passengerID, selfLocation,  movePara);
        Passenger.passengerList.add(passenger);
        Passenger.passengerIDs.add(passengerID);
        RedisHelper.addNewPassenger(passengerID);
        return passenger;
    }

    public static Driver createNewDriver() {
        String driverID = generateDriverID();
        //double[] location = {42.3508011,-71.1399532};
        double[] selfLocation = {42.3508011,-71.1399532};
        double[] pasLocation = NULL_LOCATION;
        double[] destiLocation = NULL_LOCATION;
        double[] movePara = {1e-4, Math.abs(1e-4 / Math.cos(selfLocation[0]))};
        Driver driver = new Driver(driverID, selfLocation, 1, movePara, pasLocation, destiLocation, timeStamp(), null);
        Driver.getDriverList().add(driver);
        Driver.getDriverIDs().add(driverID);
        RedisHelper.addNewDriver(driverID);
        return driver;
    }

    public static Passenger createNewPassenger() {
        String passengerID = generatePassengerID();
        //double[] location = {42.3508011,-71.1399532};
        double[] selfLocation = {42.3493101,-71.1075554};
        double[] destiLocation = {42.3515459,-71.0664048};
        double[] movePara = {8.4e-6, Math.abs(8.4e-6 / Math.cos(selfLocation[0]))};
        Passenger passenger = new Passenger(passengerID, selfLocation, 1, movePara, destiLocation, timeStamp(), null);
        Passenger.passengerList.add(passenger);
        Passenger.passengerIDs.add(passengerID);
        RedisHelper.addNewPassenger(passengerID);
        return passenger;
    }

    private static String timeStamp() {
        String FORMAT_FULL = "yyyy MM dd HH mm ss S";
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    private static String generateID(char type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        for (int i = 0; i < 15; i++) {
            sb.append(CANDIDATE_CHARS[(int)(Math.random()*(CANDIDATE_CHARS.length-1))]);
        }
        return sb.toString();
    }

    public static Map<String, String> generateDraftDriverMap(Driver driver) {
        Map<String, String> info = new HashMap<>();
        info.put("current_location", location2String(driver.getCurrentLocation()));
        return info;
    }

    public static Map<String, String> generateDraftPassengerMap(Passenger passenger) {
        Map<String, String> info = new HashMap<>();
        info.put("current_location", location2String(passenger.getCurrentLocation()));
        return info;
    }

    public static Map<String, String> generateDriverMap(Driver driver) {
        Map<String, String> info = new HashMap<>();
        boolean hasPas = driver.getAssignedPassenger() != null;
        info.put("current_location", location2String(driver.getCurrentLocation()));
        info.put("state", driver.getState()+"");
        info.put("pass_id", hasPas ? driver.getAssignedPassenger().getId() : "nan");
        info.put("pass_location", (hasPas ? location2String(driver.getAssignedPassenger().getCurrentLocation()) : location2String(NULL_LOCATION)));
        info.put("pass_destination", (hasPas ? location2String(driver.getAssignedPassenger().getDestinationLocation()) : location2String(NULL_LOCATION)));
        info.put("time_info", driver.getTimeStamp());
        return info;
    }

    public static Map<String, String> generatePassengerMap(Passenger passenger) {
        Map<String, String> info = new HashMap<>();
        boolean hasDriver = passenger.getAssignedDriver() != null;
        info.put("current_location", location2String(passenger.getCurrentLocation()));
        info.put("state", passenger.getState()+"");
        info.put("driver_id", hasDriver ? passenger.getAssignedDriver().getId() : "nan");
        info.put("destination", location2String(passenger.getDestinationLocation()));
        info.put("time_info", passenger.getTimeStamp());
        return info;
    }

    private static String location2String(double[] location) {
        StringBuilder sb = new StringBuilder();
        sb.append(location[0]);
        sb.append("   ");
        sb.append(location[1]);
        return sb.toString();
    }

    private static void generateMoveLocation(double[] location, double[] movePara) {
        location[0] += ((Math.random() > 0.5) ? 1 : -1) * Math.random() * movePara[0];
        location[0] = (location[0] > TOPRIGHT[0]) ? TOPRIGHT[0] : location[0];
        location[0] = (location[0] < BOTTOMLEFT[0]) ? BOTTOMLEFT[0] : location[0];

        location[1] += ((Math.random() > 0.5) ? 1 : -1) * Math.random() * movePara[1];
        location[1] = location[1] > TOPRIGHT[1] ? TOPRIGHT[1] : location[1];
        location[1] = location[1] < BOTTOMLEFT[1] ? BOTTOMLEFT[1] : location[1];
    }

    private static double[] generateRandomLocation() {

        double latiDistance = TOPRIGHT[0] - BOTTOMLEFT[0];
        double longiDistance = TOPRIGHT[1] - BOTTOMLEFT[1];

        double[] location = {(BOTTOMLEFT[0] + (Math.random()*latiDistance)), (BOTTOMLEFT[1] + (Math.random()*longiDistance))};
        //System.out.println(location[0]);

        return location;

    }


}
