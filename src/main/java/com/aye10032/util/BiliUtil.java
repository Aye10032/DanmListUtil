package com.aye10032.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BiliUtil {

    static String api_up = "https://api.bilibili.com/x/web-interface/card?mid=";
    static String api_video = "http://space.bilibili.com/ajax/member/getSubmitVideos?mid=";
    static String api_danmu = "https://api.bilibili.com/x/v1/dm/list.so?oid=";

    CloseableHttpClient httpclient;

    public BiliUtil() {
        httpclient = HttpClients.createDefault();
    }

    public List<String> getVideoList(String UUID, int pageSize) {
        List<String> videolist = new ArrayList<String>();

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(api_video).append(UUID).append("&pagesize=").append(pageSize);

        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(new String(urlBuilder));

            response = httpclient.execute(httpget);

            String body = null;
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {//判断状态码
                HttpEntity httpEntity = response.getEntity(); //获取返回body
                body = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
                System.out.println("result:" + body);
            }

            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(body);

            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();

                JsonArray vlist = jsonObject.getAsJsonObject("data").getAsJsonArray("vlist");

                for (JsonElement listElement : vlist) {
                    if (listElement.isJsonObject()) {
                        JsonObject listObject = listElement.getAsJsonObject();

                        videolist.add(listObject.get("aid").getAsString());
                    }
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close(); //释放连接
                }
            } catch (Exception e) {
            }
            try {
                if (httpclient != null) {
                    httpclient.close();//关闭客户端
                }
            } catch (Exception e) {
            }
        }

        return videolist;
    }

    public List<String> getVideoList(String UUID) {
        List<String> videolist = new ArrayList<String>();

        return getVideoList(UUID, 10);
    }

    public String getDanmu(String aid){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(api_danmu).append(aid);

        HttpGet httpget = new HttpGet(new String(urlBuilder));

        CloseableHttpResponse response = null;
        String body = null;
        try {
            response = httpclient.execute(httpget);


            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                HttpEntity httpEntity = response.getEntity();
                body = EntityUtils.toString(httpEntity,"UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(response != null){
                    response.close(); //释放连接
                }
            }catch(Exception e){}
            try{
                if(httpclient != null){
                    httpclient.close();//关闭客户端
                }
            }catch(Exception e){}
        }

        return body;
    }

}
