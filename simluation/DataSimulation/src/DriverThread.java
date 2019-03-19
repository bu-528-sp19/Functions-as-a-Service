public class DriverThread extends Thread {

    int instanceNum;
    final RedisHelper redisHelper;
    Driver[] drivers;

    public DriverThread(int num, RedisHelper redisHelper) {
        super();
        this.instanceNum = num;
        this.redisHelper = redisHelper;
        this.drivers = new Driver[instanceNum];
    }

    @Override
    public void run() {
        synchronized (redisHelper) {
            for (int i = 0; i < instanceNum; i++) {
                drivers[i] = DataLayerHelper.createDraftDriver();
            }
        }
        System.out.println("1");

        while (true) {
            synchronized (redisHelper) {
                try {
                    for (Driver driver : drivers) {
                        DataLayerHelper.randomMoveDriver(driver);
                        System.out.println(PostHelper.sendPost(DataLayerHelper.UPDATE_DRIVER_LINK, DataLayerHelper.wrapDriverJson(driver)));
                    }
                    System.out.println("driver sleep");
                    redisHelper.notify();
                    redisHelper.wait();
                } catch (Exception e) {

                }
            }
        }
    }



}
