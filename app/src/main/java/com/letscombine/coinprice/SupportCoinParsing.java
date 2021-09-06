package com.letscombine.coinprice;

import android.text.TextUtils;
import android.util.Log;

import com.letscombine.coinprice.define.StringDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SupportCoinParsing {

    public static ArrayList<String> parsingData(String data, int exchangeIndex) {
        ArrayList<String> parsingDataList = new ArrayList<>();
        JSONObject nonParsingObject = null;
        JSONObject parsingObject = null;
        JSONArray parsingArray = null;
        Iterator iterator = null;
        try {
            if (!TextUtils.isEmpty(data)) {
                // API 호출하여 받아온 데이터를 JsonObject에 담는다.
                if (exchangeIndex != 4 && exchangeIndex != 7) { //  upbit는 object 형태가 아님
                    nonParsingObject = new JSONObject(data);
                }
                switch (exchangeIndex) {
                    case 1: // coinOne
                        // Json 내 바로 key 값이 코인명이기 때문에 key값만 추린다.
                        iterator = nonParsingObject.keys();
                        while (iterator.hasNext()) {
                            parsingDataList.add(iterator.next().toString().toUpperCase() + "/" + StringDefine.KRW);
                        }
                        // 키값 중 0, 1, 2번 인덱스에 있는 값은 코인 명이 아닌
                        // 통신 결과, 에러코드, 호출 시간이므로 삭제
                        parsingDataList.remove(2);
                        parsingDataList.remove(1);
                        parsingDataList.remove(0);
                        break;
                    case 2: // mexc
                    case 6: // huobi
                        parsingArray = (JSONArray) nonParsingObject.get(StringDefine.DATA);
                        for (int dInx = 0; dInx < parsingArray.length(); dInx++) {
                            parsingObject = new JSONObject(parsingArray.getString(dInx));
                            // mexc와 huobi의 response데이터 형태는 같지만 각각 key값이 달라 각각 list에 저장
                            if (exchangeIndex == 2) {
                                parsingDataList.add(parsingObject.getString(StringDefine.SYMBOL).replace('_', '/').toUpperCase());
                            } else {
                                parsingDataList.add(parsingObject.getString(StringDefine.BASE_CURRENCY).toUpperCase() + "/" + parsingObject.getString(StringDefine.QUOTE_CURRENCY).toUpperCase());
                            }
                        }
                        break;
                    case 3: // bithumb
                        String coinData = nonParsingObject.getString(StringDefine.DATA);
                        parsingObject = new JSONObject(coinData);

                        iterator = parsingObject.keys();
                        while (iterator.hasNext()) {
                            parsingDataList.add(iterator.next().toString().toUpperCase() + "/" + StringDefine.KRW);
                        }
                        break;

                    case 4: // upbit
                    case 7: // gate.io
                        parsingArray = new JSONArray(data);
                        for (int dInx = 0; dInx < parsingArray.length(); dInx++) {
                            parsingObject = new JSONObject(parsingArray.getString(dInx));
                            if (exchangeIndex == 4) {
                                String[] coinDetail = parsingObject.getString(StringDefine.MARKET).split("-");
                                parsingDataList.add(coinDetail[1] + "/" + coinDetail[0]);
                            } else {
                                parsingDataList.add(parsingObject.getString(StringDefine.ID).replace('_', '/'));
                            }
                        }
                        break;
                    case 5: // binance
                        parsingArray = (JSONArray) nonParsingObject.get(StringDefine.SYMBOLS);
                        for (int dInx = 0; dInx < parsingArray.length(); dInx++) {
                            parsingObject = new JSONObject(parsingArray.getString(dInx));
                            if (StringDefine.TRADING.equals(parsingObject.getString(StringDefine.STATUS))) {
                                parsingDataList.add(parsingObject.getString(StringDefine.SYMBOL).toUpperCase());
                            }
                        }
                        break;
                }
                return parsingDataList;

            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
