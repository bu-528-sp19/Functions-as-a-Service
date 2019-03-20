import redis.clients.jedis.Jedis;

public class RedisHelper {

    private static Jedis passengerInfoConnection;
    private static Jedis driverInfoConnection;
    private static Jedis passengerListConnection;
    private static Jedis driverListConnection;

    public RedisHelper(){
        init();
    }
    public RedisHelper(int i){
    }

    private void init() {
        passengerInfoConnection = new Jedis("localhost", 6379);
        passengerInfoConnection.select(0);
        driverInfoConnection = new Jedis("localhost", 6379);
        driverInfoConnection.select(1);
        passengerListConnection = new Jedis("localhost", 6379);
        passengerListConnection.select(2);
        driverListConnection = new Jedis("localhost", 6379);
        driverListConnection.select(3);
    }

    public static Jedis init(int db) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.select(db);
        return jedis;
    }

    public static void request4DraftDriver(Driver driver) {
        driverInfoConnection.hset(driver.getId(),DataLayerHelper.generateDraftDriverMap(driver));
    }

    public static void request4DraftPassenger(Passenger passenger) {
        passengerInfoConnection.hset(passenger.getId(),DataLayerHelper.generateDraftPassengerMap(passenger));
    }

    public static void request4Driver(Driver driver) {
        driverInfoConnection.hmset(driver.getId(),DataLayerHelper.generateDriverMap(driver));
    }

    public static void request4Passenger(Passenger passenger) {
        passengerInfoConnection.hmset(passenger.getId(),DataLayerHelper.generatePassengerMap(passenger));
    }

    public static void addNewDriver(String id) {
        driverListConnection.sadd("list", id);
    }

    public static void addNewPassenger(String id) {
        passengerListConnection.sadd("list", id);
    }

    public static void deleteDriver(String id) {
        driverInfoConnection.del(id);
    }

    public static void deletePassenger(String id) {
        System.out.println(passengerInfoConnection.del(id));
    }

    public static Jedis getPassengerInfoConnection() {
        return passengerInfoConnection;
    }

    public static Jedis getDriverInfoConnection() {
        return driverInfoConnection;
    }

    public static Jedis getPassengerListConnection() {
        return passengerListConnection;
    }

    public static Jedis getDriverListConnection() {
        return driverListConnection;
    }
}
