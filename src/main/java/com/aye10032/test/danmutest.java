package com.aye10032.test;

import java.io.*;
import java.util.Arrays;

import okhttp3.*;

public class danmutest {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.bilibili.com/x/v1/dm/list.so?oid=171147896")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(new String(response.body().bytes()));

    }


}
