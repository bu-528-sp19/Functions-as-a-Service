import java.util.ArrayList;

class PassengerScheduleRunnable implements Runnable {
    int nums;
    String name;
    int cal;
    ArrayList<Passenger> passengers;


    public PassengerScheduleRunnable(int nums, String name) {
        this.nums = nums;
        this.name = name;
        this.passengers = new ArrayList<>();
        for (int i = 0; i < nums; i++) {
            passengers.add(DataLayerHelper.createNewPassenger());
        }
    }

    @Override
    public void run() {
        for (Passenger passenger : passengers) {
            try {
                DataLayerHelper.randomMovePassenger(passenger);
                PostHelper.sendPost(DataLayerHelper.UPDATE_PASSENGER_LINK, DataLayerHelper.wrapPassengerJson(passenger));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Main.totalTimes.addAndGet(passengers.size());
    }
}


