import java.util.HashSet;

public class Driver {

    public static HashSet<Driver> driverList = new HashSet<>();
    public static HashSet<String> driverIDs = new HashSet<>();


    private String id;
    private int state;
    private double[] currentLocation;
    private double[] movePara; //[lati, longi]
    private double[] destiLocation;


    public Driver(String id, int state, double[] currentLocation, double[] movePara) {
        this.id = id;
        this.state = state;
        this.currentLocation = currentLocation;
        this.movePara = movePara;
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

    public double[] getDestiLocation() {
        return destiLocation;
    }

    public void setDestiLocation(double[] destiLocation) {
        this.destiLocation = destiLocation;
    }
}
