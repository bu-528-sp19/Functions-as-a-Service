import org.json.JSONObject;

public class PostTest {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("driver_id", "D1");
        jsonObject.put("latitude", "30");
        jsonObject.put("longitude", "30");
        String url = "";
        String query = "";

        url = "https://192.168.99.100:31001/api/v1/web/guest/default/updateDriver";
        //url = "https://www.baidu.com";
        query = jsonObject.toString();



        System.out.println(PostHelper.sendPost(url, query));








    }
}
