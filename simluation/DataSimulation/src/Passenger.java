import java.util.HashSet;

public class Passenger {

    private static HashSet<Driver> passengerList;
    private static HashSet<String> passengerIDs;


    private String id;
    private double[] currentLocation;

    public static HashSet<Driver> getPassengerList() {
        return passengerList;
    }

    public static void setPassengerList(HashSet<Driver> passengerList) {
        Passenger.passengerList = passengerList;
    }

    public static HashSet<String> getPassengerIDs() {
        return passengerIDs;
    }

    public static void setPassengerIDs(HashSet<String> passengerIDs) {
        Passenger.passengerIDs = passengerIDs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(double[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double[] getMovePara() {
        return movePara;
    }

    public void setMovePara(double[] movePara) {
        this.movePara = movePara;
    }

    public double[] getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(double[] destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(Driver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    private int state;

    public Passenger(String id, double[] currentLocation, int state, double[] movePara, double[] destinationLocation, String timeStamp, Driver assignedDriver) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.state = state;
        this.movePara = movePara;
        this.destinationLocation = destinationLocation;
        this.timeStamp = timeStamp;
        this.assignedDriver = assignedDriver;
    }

    private double[] movePara; //[lati, longi]
    private double[] destinationLocation;
    private String timeStamp;
    private Driver assignedDriver;


}
