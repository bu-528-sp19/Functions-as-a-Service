import java.util.HashSet;

public class Passenger {

    public static HashSet<Passenger> passengerList = new HashSet<>();
    public static HashSet<String> passengerIDs = new HashSet<>();

    private String id;
    private int state;
    private double[] currentLocation;
    private double[] movePara; //[lati, longi]
    private double[] destinationLocation;
    private double[] driverLocation;

    public Passenger(String id, int state, double[] currentLocation, double[] movePara, double[] destinationLocation) {
        this.id = id;
        this.state = state;
        this.currentLocation = currentLocation;
        this.movePara = movePara;
        this.destinationLocation = destinationLocation;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public double[] getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(double[] driverLocation) {
        this.driverLocation = driverLocation;
    }

}
