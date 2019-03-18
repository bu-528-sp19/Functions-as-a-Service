public class PassengerThread extends Thread {

    int instanceNum;
    final RedisHelper redisHelper;
    Passenger[] passengers;

    public PassengerThread(int num,RedisHelper redisHelper) {
        super();
        this.instanceNum = num;
        this.redisHelper = redisHelper;
        this.passengers = new Passenger[instanceNum];
    }

    @Override
    public void run() {

        synchronized (redisHelper) {
            for (int i = 0; i < instanceNum; i++) {
                passengers[i] = DataLayerHelper.createDraftPassenger();
            }
        }

        System.out.println("2");

        while (true) {
            synchronized (redisHelper) {
                try {
                    for (Passenger passenger : passengers) {
                        DataLayerHelper.randomMovePassenger(passenger);
                        RedisHelper.request4DraftPassenger(passenger);
                    }
                    redisHelper.notifyAll();
                    redisHelper.wait();
                } catch (Exception e){

                }

            }
        }
    }
}
