import java.util.*;
import com.google.gson.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;



//import org.json.JSONObject;

public class LatencyTest {
    public static void main(String[] args){
        System.out.println("Try to measuer the Latency for this simple post program");

        //measuring gap
        int measureGapInSecond = 1;


        PostHelper ph = new PostHelper();
        String url = "https://openwhisk.faas.compulty.com:31001/api/v1/web/guest/default/helloweb";

        //String url2 = "https://openwhisk.faas.compulty.com:31001/api/v1/web/guest/default/searchPassenger";
        //String result = ph.sendPost("https://openwhisk.faas.compulty.com:31001", "");
        //System.out.println(result);




        JsonObject response = new JsonObject();
        //response.addProperty("greeting", "Hello");
        String input = "{\"name\": \"World\"}";

        //continue version for test
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            String result = ph.sendPost(url, input);
            long intervalTime = System.currentTimeMillis() - startTime;

            System.out.println(result);
            System.out.println("Time lapse is: " + intervalTime);
        }

        //timer version
        Timer timer = new Timer();
        List<Integer> times = new ArrayList<>();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                String result = ph.sendPost(url, input);
                long intervalTime = System.currentTimeMillis() - startTime;
                times.add((int)intervalTime);
                System.out.println("Time lapse is: " + intervalTime);

            }
        },1000, 1000 * measureGapInSecond);



        /*
        try {
            trustAllHosts();
            URL url = new URL("https://openwhisk.faas.compulty.com:31001");
            HttpURLConnection hConn = (HttpURLConnection) url.openConnection();
            hConn.setHostnameVerifier(DO_NOT_VERIFY);
            hConn.setRequestProperty("Content-Type", "application/json");
            hConn.setRequestMethod("POST");
            hConn.setDoOutput(true);

            String input = "{\"name\": \"World\"}";
            OutputStream os = hConn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            os.close();

            if (hConn.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(hConn.getInputStream()));
                String output;
                while((output = br.readLine()) != null){
                    System.out.println(output);
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        */
    }
}
