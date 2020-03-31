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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class MainRun {

    public static void main(String[] args) {
        try {
            String xmlStr = new BiliUtil().getDanmu("171147896");

            Document document = DocumentHelper.parseText(xmlStr);

            Element rootElm = document.getRootElement();

            List nodes = rootElm.elements("d");
            for (Iterator it = nodes.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();

                System.out.print(elm.attributeValue("p").split(",")[6]);
                System.out.println(" " + elm.getText());
                // do something
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
