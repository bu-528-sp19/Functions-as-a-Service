import org.json.JSONObject;

public class PostTest {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("driver_id", "D1");
        jsonObject.put("latitude", "30");
        jsonObject.put("longitude", "30");
        String url = "";
        String query = "";

        //url = "https://192.168.99.100:31001/api/v1/web/guest/default/updateDriver";
        //url = "https://www.baidu.com";
        url = "https://openwhisk.faas.compulty.com:31001/api/v1/web/guest/default/updateDriver";
        query = jsonObject.toString();




        long startTime =  System.currentTimeMillis();
        for(int i = 0; i < 500; i++) {
            PostHelper.sendPost(url, query);
            //FastPostHelper.sendPost(url, query);
            if (i%100 == 0) System.out.println(i);

        }
        long endTime =  System.currentTimeMillis();
        System.out.println((endTime-startTime)/1000);

        //System.out.println(PostHelper.sendPost(url, query));
        startTime =  System.currentTimeMillis();
        for(int i = 0; i < 500; i++) {
            FastPostHelper.sendPost(url, query);
            //PostHelper.sendPost(url, query);

            if (i%100 == 0) System.out.println(i);
        }
        endTime =  System.currentTimeMillis();
        System.out.println((endTime-startTime)/1000);
    }
}
