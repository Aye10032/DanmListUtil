import com.aye10032.util.BiliUtil;
import com.aye10032.util.FileUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainRun {

    public static void main(String[] args) {
        new BiliUtil().getVideoList("40077740");

        /*try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.bilibili.com/x/v1/dm/list.so?oid=171147896")
                    .method("GET", null)
                    .addHeader("Host", "api.bilibili.com")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Cache-Control", "max-age=0")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("Accept", "text/xml")
                    .addHeader("Accept-Encoding","gzip, deflate, br")
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36")
                    .build();
            Response response = client.newCall(request).execute();

            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
