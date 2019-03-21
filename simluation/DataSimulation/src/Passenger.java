import java.util.HashSet;

public class Passenger {

    public static HashSet<Passenger> passengerList = new HashSet<>();
    public static HashSet<String> passengerIDs = new HashSet<>();

    private String id;
    private double[] currentLocation;
    private double[] movePara; //[lati, longi]
    private double[] destinationLocation;
    private int state;
    private String timeStamp;
    private Driver assignedDriver;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public Passenger(String id, double[] currentLocation, int state, double[] movePara, double[] destinationLocation, String timeStamp, Driver assignedDriver) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.state = state;
        this.movePara = movePara;
        this.destinationLocation = destinationLocation;
        this.timeStamp = timeStamp;
        this.assignedDriver = assignedDriver;
    }

    public Passenger(String id, double[] currentLocation, double[] movePara) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.movePara = movePara;
    }
}
