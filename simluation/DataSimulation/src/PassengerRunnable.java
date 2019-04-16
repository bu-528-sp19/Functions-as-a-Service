import java.util.ArrayList;

class PassengerRunnable implements Runnable {
    int maximum;
    int increment;
    ArrayList<Passenger> passengers;


    public PassengerRunnable (int maximum, int increment) {
        this.maximum = maximum;
        this.increment = increment;
        this.passengers = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(passengers.size());
            if (passengers.size() < maximum) {
                for (int i = 0; i < increment; i++) {
                    passengers.add(DataLayerHelper.createNewPassenger());
                }
            }
            for (Passenger passenger: passengers) {
                DataLayerHelper.randomMovePassenger(passenger);
                System.out.println(PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapPassengerJson(passenger)));
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


