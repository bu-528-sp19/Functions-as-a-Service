public class DriverThread extends Thread {

    int instanceNum;
    Driver[] drivers;

    public DriverThread(int num) {
        super();
        this.instanceNum = num;
        this.drivers = new Driver[instanceNum];
    }

    @Override
    public void run() {

        for (int i = 0; i < drivers.length; i++) {
            drivers[i] = DataLayerHelper.createDraftDriver();
        }

        while (true) {
            for (Driver driver: drivers) {
                DataLayerHelper.randomMoveDriver(driver);
                //PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapDriverJson(driver));
                System.out.println(PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapDriverJson(driver)));
            }
        }


    }
}
