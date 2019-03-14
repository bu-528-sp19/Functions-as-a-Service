public class DataLayerHelper {

    //all candidate chars that would be using in generating
    public static final String[] CANDIDATE_CHARS =  new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    //usually a person can move 56m per minute, equals to 5.068e-4 latitude, equals to 56/111000*cos(latitude) longitude
    // basic simulation: 42.3508011,-71.1399532 -> 42.3493101,-71.1075554

    public static String generateID(char type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        for (int i = 0; i < 15; i++) {
            sb.append(CANDIDATE_CHARS[(int)(Math.random()*(CANDIDATE_CHARS.length-1))]);
        }
        return sb.toString();
    }

    public static String generateDriverID() {
        String resultID = DataLayerHelper.generateID('D');
        while (Driver.driverIDs.contains(resultID)) {
            resultID = DataLayerHelper.generateID('D');
        }
        return resultID;
    }

    public static String generatePassengerID() {
        String resultID = DataLayerHelper.generateID('D');
        while (Driver.driverIDs.contains(resultID)) {
            resultID = DataLayerHelper.generateID('D');
        }
        return resultID;
    }

    public static Driver createNewDriver() {
        String driverID = generateDriverID();
        double[] location = {42.3508011,-71.1399532};
        Driver driver = new Driver(driverID, location, 0, 0, 0);
        Driver.driverList.add(driver);
        Driver.driverIDs.add(driverID);
        return driver;
    }






}
