import org.json.JSONObject;

public class PostTest {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "id1");
        jsonObject.put("latitude", "id1");
        jsonObject.put("longitude", "id2");

        String url = "https://www.baidu.com/";
        String query = jsonObject.toString();

        System.out.println(PostHelper.sendPost(url, query));



    }
}
