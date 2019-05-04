import org.json.JSONObject;

import java.util.ArrayList;

class DriverScheduleRunnable implements Runnable {
    int nums;
    String name;
    int cal;
    ArrayList<Driver> drivers;


    public DriverScheduleRunnable(int nums, String name) {
        this.nums = nums;
        this.name = name;
        this.drivers = new ArrayList<>();
        for (int i = 0; i < nums; i++) {
            drivers.add(DataLayerHelper.createNewDriver());
        }
    }

    @Override
    public void run() {
        for (Driver driver : drivers) {
            try {
                DataLayerHelper.randomMoveDriver(driver);
                PostHelper.sendPost(DataLayerHelper.UPDATE_DRIVER_LINK, DataLayerHelper.wrapDriverJson(driver));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       Main.totalTimes.addAndGet(drivers.size());
    }
}
