package com.aye10032.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BiliUtil {

    static String api_up = "https://api.bilibili.com/x/web-interface/card?mid=";
    static String api_video = "http://space.bilibili.com/ajax/member/getSubmitVideos?mid=";

    OkHttpClient client = null;

    public BiliUtil(){

    }

    public List<String> getVideoList(String UUID, int pageSize){
        List<String> videolist = new ArrayList<String>();

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(api_video).append(UUID).append("&pagesize=").append(pageSize);

        try {
            client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(new String(urlBuilder))
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();

            System.out.println(response.body().string());

            String body = null;
            if (response.body() != null){
                body = new String(response.body().bytes());
            }

            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(body);

            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();

                JsonArray vlist = jsonObject.getAsJsonObject("data").getAsJsonArray("vlist");

                for (JsonElement listElement:vlist){
                    if (listElement.isJsonObject()){
                        JsonObject listObject = listElement.getAsJsonObject();

                        videolist.add(listObject.get("aid").getAsString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videolist;
    }

    public List<String> getVideoList(String UUID){
        List<String> videolist = new ArrayList<String>();

        return getVideoList(UUID,10);
    }

}
