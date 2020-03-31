import com.aye10032.util.BiliUtil;
import com.aye10032.util.CRC32Util;

import java.io.StringReader;
import java.util.List;


public class MainRun {

    public static void main(String[] args) {
        CRC32Util crc32Util = new CRC32Util();
        List<String> videos = new BiliUtil().getVideoList("1311124",20);

        System.out.println(videos);

        for (String aid : videos) {
            System.out.println("https://www.bilibili.com/video/av"+aid);
            List<String> cidList = new BiliUtil().getCid(aid);
            for (String cid:cidList){
                String xmlString = new BiliUtil().getDanmu(cid);
                List<String[]> danmu = new BiliUtil().getDanmuData(xmlString);
                for (String[] danmudata : danmu) {
                    if (danmudata[0].contains("TIS") || danmudata[0].contains("tis") || danmudata[0].contains("Tis")) {
                        System.out.println(danmudata[0] + "(" + danmudata[1] + "->" + crc32Util.solve(danmudata[1]) + ")");
                    }
                }
            }
        }
    }

}
