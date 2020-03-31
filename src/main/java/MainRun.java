import com.aye10032.util.BiliUtil;
import com.aye10032.util.FileUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class MainRun {

    public static void main(String[] args) {
        String xmlStr = new BiliUtil().getDanmu("171147896");
    }

}
