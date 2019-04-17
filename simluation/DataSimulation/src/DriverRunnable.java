import org.json.JSONObject;

import java.util.ArrayList;

class DriverRunnable implements Runnable {
    int maximum;
    int increment;
    ArrayList<Driver> drivers;


    public DriverRunnable (int maximum, int increment) {
        this.maximum = maximum;
        this.increment = increment;
        this.drivers = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(drivers.size());
            if (drivers.size() < maximum) {
                for (int i = 0; i < increment; i++) {
                    drivers.add(DataLayerHelper.createNewDriver());
                }
            }
            System.out.println(drivers.size());
            for (Driver driver : drivers) {
                try {
                    DataLayerHelper.randomMoveDriver(driver);
                    JSONObject tempJson = new
                            JSONObject(PostHelper.sendPost(DataLayerHelper.UPDATE_DRIVER_LINK, DataLayerHelper.wrapDriverJson(driver)));
                    //System.out.println(tempJson.getString("content"));
                    System.out.println(tempJson.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
