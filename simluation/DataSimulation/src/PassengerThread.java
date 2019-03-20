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

        for (Passenger passenger: passengers) {
            DataLayerHelper.randomMovePassenger(passenger);
            PostHelper.sendPost(DataLayerHelper.UPDATE_PASSENGER_LINK, DataLayerHelper.wrapPassengerJson(passenger));
        }

    }
}
