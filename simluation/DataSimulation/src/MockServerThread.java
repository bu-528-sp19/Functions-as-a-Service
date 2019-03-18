import redis.clients.jedis.Jedis;

public class MockServerThread extends Thread{

    private String id;

    public MockServerThread(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        Jedis driverConnection = RedisHelper.newConnect(0);
        while (true) {
            System.out.println("Current Location: " + driverConnection.get(id));
        }
    }
}
