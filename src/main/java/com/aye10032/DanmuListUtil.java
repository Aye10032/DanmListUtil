package com.aye10032;

import com.aye10032.data.*;
import com.aye10032.util.BiliUtil;
import com.aye10032.util.CRC32Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class DanmuListUtil {

    UpListClass upListClass;
    DanmuClass danmuClass;
    CRC32Util crc32Util;

    public DanmuListUtil() {
        upListClass = ConfigLoader.load(".\\data\\uplist.json", UpListClass.class);
        danmuClass = ConfigLoader.load(".\\data\\danmulist.json", DanmuClass.class);
        crc32Util = new CRC32Util();

        String[] aim = new String[]{"TIS","Tis","tis"};

        getAimDanmu(aim);

//        updateUpFromFollow("392055878");

    }

    public void updateUpFromFollow(String UUID) {
        String body = "";
        int count = 0;

        count = new BiliUtil().getUpInfo(UUID)
                .get("data").getAsJsonObject()
                .get("card").getAsJsonObject()
                .get("friend").getAsInt();
        int i = 1;
        while (count >= 20) {
            body = new BiliUtil().getUplist(UUID, i);

            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(body);


            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();

                JsonArray vlist = jsonObject.getAsJsonObject("data").getAsJsonArray("list");

                for (JsonElement listElement : vlist) {
                    if (listElement.isJsonObject()) {
                        JsonObject listObject = listElement.getAsJsonObject();

                        String mid = listObject.get("mid").getAsString();
                        String name = listObject.get("uname").getAsString();
                        String face = listObject.get("face").getAsString();
                        String sign = listObject.get("sign").getAsString();

                        UpDataClass updat = new UpDataClass(mid, name, face, sign);
                        upListClass.addUp(updat);
                    }
                }
            }

            count -= 20;
            i++;
        }
        ConfigLoader.save(".\\data\\uplist.json", UpListClass.class, upListClass);
    }

    public void getAimDanmu(String[] aims) {

        List<UpDataClass> uplist = upListClass.getUplist();

        for (UpDataClass updata : uplist) {
            String mid = updata.getMid();
            String name = updata.getName();
            System.out.println(name);

            UpVideoClass upVideoClass = new UpVideoClass();
            upVideoClass.setMid(mid);
            upVideoClass.setName(name);

            List<String[]> videos = new BiliUtil().getVideoList(mid, 20);

            for (String[] aid_title : videos) {
                String aid = aid_title[0];
                String title = aid_title[1];
                String url = "https://www.bilibili.com/video/av" + aid;

                System.out.println(url);

                VideoDanmuClass videoDanmuClass = new VideoDanmuClass();
                videoDanmuClass.setAid(aid);
                videoDanmuClass.setTitle(title);
                videoDanmuClass.setUrl(url);

                List<String> cidList = new BiliUtil().getCid(aid);
                for (String cid : cidList) {
                    String xmlString = new BiliUtil().getDanmu(cid);
                    List<String[]> danmu = new BiliUtil().getDanmuData(xmlString);
                    for (String[] danmudata : danmu) {

                        boolean flag = false;
                        for (String aim : aims) {
                            if (danmudata[0].contains(aim)) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            try {
                                String danmuStr = danmudata[0];
                                System.out.println(danmuStr);
                                String uid = crc32Util.solve(danmudata[1]);

                                JsonObject userInfo = new BiliUtil().getUpInfo(uid)
                                        .get("data").getAsJsonObject()
                                        .get("card").getAsJsonObject();

                                String face = userInfo.get("face").getAsString();
                                String dmname = userInfo.get("name").getAsString();
                                String sign = userInfo.get("sign").getAsString();

                                List<String> list = new ArrayList<String>();
                                list.add(danmuStr);

                                DanmuDataClass danmuDataClass = new DanmuDataClass(mid, dmname, face, sign, list);

                                videoDanmuClass.addDanmu(danmuDataClass);
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                upVideoClass.addVideoList(videoDanmuClass);
            }

            danmuClass.addUp(upVideoClass);
            ConfigLoader.save(".\\data\\danmulist.json", DanmuClass.class, danmuClass);
        }
    }

}
