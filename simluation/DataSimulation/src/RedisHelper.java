import redis.clients.jedis.Jedis;

public class RedisHelper {

    public static Jedis newConnect(int db) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.select(db);
        return jedis;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("ok","bixude");
        System.out.println(jedis.get("ok"));
    }
}
