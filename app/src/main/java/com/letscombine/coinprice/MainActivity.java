package com.letscombine.coinprice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.gson.Gson;
import com.letscombine.coinprice.define.AddressDefine;
import com.letscombine.coinprice.define.StringDefine;
import com.letscombine.coinprice.utils.Utils;

//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Utils utils = null;
    private Context mContext = null;
    private String returnData = null;

//    private RecyclerView recyclerViewGetRequestData = null;
    private TextView recyclerViewGetRequestData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        init();
    }

    /**
     * 기본 설정
     */
    private void init() {
        // 스피너 세팅
        setSpinner();
        ImageView imgSearchCoinData = findViewById(R.id.imgSearchCoinData);
        recyclerViewGetRequestData = findViewById(R.id.recyclerViewGetRequestData);
    }

    /**
     * 스피너 설정
     */
    private void setSpinner() {
        Spinner spnSelectExchange = findViewById(R.id.spnSelectExchange);
        Spinner spnSelectCoin = findViewById(R.id.spnSelectCoin);
        spnSelectExchange.setOnItemSelectedListener(this);
        spnSelectCoin.setOnItemSelectedListener(this);

        String[] coinExchange = getResources().getStringArray(R.array.coinExchange);
        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, coinExchange);
        spnSelectExchange.setAdapter(adapter);
    }

    /**
     * API 호출 후 return데이터 보여주기
     * @param selectExchange
     */
    private void callApi(String selectExchange) {
        CallApiThread searchThread = new CallApiThread();
        if (!TextUtils.isEmpty(selectExchange)) {
            searchThread.setSelectExchange(selectExchange);
        } else {
            return;
        }
        searchThread.start();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                CallApiThread searchThread = new CallApiThread();
//                if (!TextUtils.isEmpty(selectExchange)) {
//                    searchThread.setSelectExchange(selectExchange);
//                } else {
//                    return;
//                }
//                searchThread.start();
//            }
//        });
    }

    /**
     * 스피너 선택 시 동작
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            // 거래소 선택 시 동작
            case R.id.spnSelectExchange:
//                sSelectExchange = (String) parent.getItemAtPosition(position);
                callApi((String) parent.getItemAtPosition(position));
                Toast.makeText(mContext, (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                break;
            // 코인 선택 시 동작
            case R.id.spnSelectCoin:
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 네트워크 통신용 thread 작성
     */
    public class CallApiThread extends Thread {
        // 선택한 코인
        private String sSelectExchange = null;
        private String responseData = null;
        private ArrayList<String> coinList = new ArrayList<>();

        // 선택한 코인 세팅
        private void setSelectExchange(String exchange) {
            sSelectExchange = exchange;
        }

        @Override
        public void run() {
            super.run();
            utils = new Utils();
            HashMap<String, String> hashMap = new HashMap<>();
            String url = null;
            switch (sSelectExchange) {
                case StringDefine.COINONE:
//                    url = AddressDefine.COINONE_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.COINONE_ALL_COIN, hashMap), 1);
                    break;
                case StringDefine.MEXC:
//                    url = AddressDefine.MEXC_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.MEXC_ALL_COIN, hashMap), 2);
                    break;
                case StringDefine.BITHUMB:
//                    url = AddressDefine.BITHUMB_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.BITHUMB_ALL_COIN, hashMap), 3);
                    break;
                case StringDefine.UPBIT:
//                    url = AddressDefine.UPBIT_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.UPBIT_ALL_COIN, hashMap), 4);
                    break;
                case StringDefine.BINANCE:
//                    url = AddressDefine.BINANCE_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.BINANCE_ALL_COIN, hashMap), 5);
                    break;
                case StringDefine.HUOBI:
//                    url = AddressDefine.HUOBI_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.HUOBI_ALL_COIN, hashMap), 6);
                    break;
                case StringDefine.GATEIO:
//                    url = AddressDefine.GATEIO_ALL_COIN;
                    coinList = SupportCoinParsing.parsingData(utils.callOkHttp(AddressDefine.GATEIO_ALL_COIN, hashMap), 7);
                    break;
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 파싱 데이터가 존재 할 시 spinner에 담는다.
                    if (coinList != null) {
                        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, coinList);
                        Spinner spnSelectCoin = findViewById(R.id.spnSelectCoin);
                        spnSelectCoin.setAdapter(adapter);
                    }
                }
            });
        }
    }
}