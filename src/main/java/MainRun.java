import com.aye10032.util.BiliUtil;
import com.aye10032.util.CRC32Util;

import java.io.StringReader;
import java.util.List;


public class MainRun {

    public static void main(String[] args) {
        List<String> cidList = new BiliUtil().getCid("2599661");
    }

}
