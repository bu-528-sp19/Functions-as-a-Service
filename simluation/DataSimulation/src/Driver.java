import java.util.HashSet;

public class Driver {

    public static HashSet<Driver> driverList;
    public static HashSet<String> driverIDs;

    public double[] location;
    public String id;
    public int state;
    public double moveParaLati;
    public double moveParaLongi;

    public Driver(String id, double[] location, int state, double moveParaLati, double moveParaLongi) {
        this.id = id;
        this.location = location;
        this.state = state;
        this.moveParaLati = moveParaLati;
        this.moveParaLongi = moveParaLongi;
    }




}
