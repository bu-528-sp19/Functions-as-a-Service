public class PassengerThread extends Thread {

    int instanceNum;
    Passenger[] passengers;

    public PassengerThread(int num) {
        super();
        this.instanceNum = num;
        this.passengers = new Passenger[instanceNum];
    }

    @Override
    public void run() {

        for (int i = 0; i < passengers.length; i++) {
            passengers[i] = DataLayerHelper.createDraftPassenger();
        }

        while (true) {
            try {
                for (Passenger passenger: passengers) {
                    DataLayerHelper.randomMovePassenger(passenger);
                    //PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapDriverJson(driver));
                    Thread.sleep((int)(Math.random()*5) * 1000);
                    //System.out.println(PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapPassengerJson(passenger)));
                    System.out.println(passenger.getId());
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }


    }
}
