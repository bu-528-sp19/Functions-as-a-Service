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
            try {
                for (Driver driver: drivers) {
                    DataLayerHelper.randomMoveDriver(driver);
                    //PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapDriverJson(driver));
                    Thread.sleep((int)(Math.random()*5) * 1000);
                    //System.out.println(PostHelper.sendPost(DataLayerHelper.POST_TEST_LINK, DataLayerHelper.wrapDriverJson(driver)));
                    System.out.println(driver.getId());

                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }


    }
}
