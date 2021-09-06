package com.letscombine.coinprice.utils;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Utils {

    /**
     * okHttp 호출
     * @param url
     * @param parameter
     */
    public String callOkHttp(String url, HashMap<String, String> parameter) {
        OkHttpClient client = new OkHttpClient();

        // 전달할 URL 생성
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        // 파리미터가 있으면 파리미터를 추가한다.
        if (!parameter.isEmpty()) {
            for (Map.Entry<String, String> hashData : parameter.entrySet()) {
                builder.addQueryParameter(hashData.getKey(), hashData.getValue());
            }
        }

        // 작성한 URL을 String으로 변환
        String sSendUrl = builder.build().toString();
        // 요청 데이터 생성
        Request request = new Request.Builder().url(sSendUrl).get().build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("에러", e.getMessage() + ", " + e);

            return e.getMessage();
        }
    }
}
