public class DriverThread extends Thread{

    @Override
    public void run() {
        Driver driver = DataLayerHelper.createNewDriver();
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Driver Location");
            } catch (InterruptedException ie) {


            }
        }
    }

}
