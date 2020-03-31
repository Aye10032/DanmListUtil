package com.aye10032.util;

import com.aye10032.data.ConfigLoader;
import com.aye10032.data.UpDataClass;
import com.aye10032.data.UpListClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DanmuListUtil {

    UpListClass upListClass;

    public DanmuListUtil() {
        upListClass = ConfigLoader.load(".\\data\\uplist.json", UpListClass.class);

        updateUpFromFollow("392055878");

        ConfigLoader.save(".\\data\\uplist.json", UpListClass.class, upListClass);
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
    }

}
