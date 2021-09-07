package com.letscombine.coinprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private Spinner spnSelectExchange = null;
    private Spinner spnSelectCoin = null;

    private RecyclerView recyclerViewGetRequestData = null;

    private CoinListAdapter coinListAdapter = new CoinListAdapter();

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
        imgSearchCoinData.setOnClickListener(onClickListener);
        recyclerViewGetRequestData = findViewById(R.id.recyclerViewGetRequestData);
    }

    /**
     * 스피너 설정
     */
    private void setSpinner() {
        spnSelectExchange = findViewById(R.id.spnSelectExchange);
        spnSelectCoin = findViewById(R.id.spnSelectCoin);
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
                callApi((String) parent.getItemAtPosition(position));
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
     * onClickListener
     * imgSearchCoinData -> 선택한 항목 추가
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgSearchCoinData:
                    String selectExchange = (String) spnSelectExchange.getSelectedItem();
                    String selectCoin = (String) spnSelectCoin.getSelectedItem();
                    CallApiThread callApiThread = new CallApiThread();
                    callApiThread.setCoinDetail(selectExchange, selectCoin, false);
                    callApiThread.start();
                    Toast.makeText(mContext, selectExchange + ", " + selectCoin, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 네트워크 통신용 thread 작성
     */
    public class CallApiThread extends Thread {
        // 선택한 코인
        private String sSelectExchange = null;
        private String sSelectCoin = null;
        private ArrayList<String> coinList = new ArrayList<>();
//        private ArrayList<CoinDetailVO> coinDetail = new ArrayList<>();
        private CoinDetailVO coinDetail = null;
        private Boolean callApiKinds = true;

        // 선택한 코인 세팅
        private void setSelectExchange(String exchange) {
            sSelectExchange = exchange;
        }

        // 추가 버튼 클릭 시 검색을 위한 데이터 세팅
        private void setCoinDetail(String sSelectExchange, String sSelectCoin, Boolean callApiKinds) {
            this.sSelectExchange = sSelectExchange;
            this.sSelectCoin = sSelectCoin;
            this.callApiKinds = callApiKinds;
        }

        
        @Override
        public void run() {
            super.run();
            utils = new Utils();
            HashMap<String, String> hashMap = new HashMap<>();
            if (callApiKinds) {
                // 코인 리스트 조회
                switch (sSelectExchange) {
                    case StringDefine.COINONE:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.COINONE_ALL_COIN, hashMap), 1);
                        break;
                    case StringDefine.MEXC:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.MEXC_ALL_COIN, hashMap), 2);
                        break;
                    case StringDefine.BITHUMB:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.BITHUMB_ALL_COIN, hashMap), 3);
                        break;
                    case StringDefine.UPBIT:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.UPBIT_ALL_COIN, hashMap), 4);
                        break;
                    case StringDefine.BINANCE:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.BINANCE_ALL_COIN, hashMap), 5);
                        break;
                    case StringDefine.HUOBI:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.HUOBI_ALL_COIN, hashMap), 6);
                        break;
                    case StringDefine.GATEIO:
                        coinList = SupportCoinParsing.parsingCoinList(utils.callOkHttp(AddressDefine.GATEIO_ALL_COIN, hashMap), 7);
                        break;
                }
                // 통신 후 결과값을 코인명을 담는 spinner에 추가
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

            } else {
                // 코인 상세 조회
                switch (sSelectExchange) {
                    case StringDefine.COINONE:
                        String[] coinName = sSelectCoin.split("/");
                        hashMap.put(StringDefine.CURRENCY, coinName[0]);
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.COINONE, utils.callOkHttp(AddressDefine.COINONE_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                    case StringDefine.MEXC:
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.MEXC, utils.callOkHttp(AddressDefine.MEXC_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                    case StringDefine.BITHUMB:
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.BITHUMB, utils.callOkHttp(AddressDefine.BITHUMB_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                    case StringDefine.UPBIT:
                        String[] coinAndFair = sSelectCoin.split("/");
                        hashMap.put(StringDefine.MARKETS, coinAndFair[1] + "-" + coinAndFair[0]);
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.UPBIT, utils.callOkHttp(AddressDefine.UPBIT_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                    case StringDefine.BINANCE:
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.BINANCE, utils.callOkHttp(AddressDefine.BINANCE_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                    case StringDefine.HUOBI:
                        hashMap.put(StringDefine.SYMBOL.toLowerCase(), sSelectCoin.replace("/", "").toLowerCase());
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.HUOBI, utils.callOkHttp(AddressDefine.HUOBI_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                    case StringDefine.GATEIO:
                        coinDetail = SupportCoinParsing.parsingCoinDetail(StringDefine.GATEIO, utils.callOkHttp(AddressDefine.GATEIO_COIN_DETAIL, hashMap), sSelectCoin);
                        break;
                }

                // recyclerView에 업데이트
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        coinListAdapter.setItem(coinDetail);

                        recyclerViewGetRequestData.setLayoutManager(new LinearLayoutManager(mContext));
                        recyclerViewGetRequestData.setAdapter(coinListAdapter);
                    }
                });
            }
            callApiKinds = true;
        }
    }
}