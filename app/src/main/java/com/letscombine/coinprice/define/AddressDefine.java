package com.letscombine.coinprice.define;

public class AddressDefine {

    // 거래소에서 지원하는 모든 코인 조회
    public static final String COINONE_ALL_COIN = "https://api.coinone.co.kr/ticker?currency=all";
    public static final String BITHUMB_ALL_COIN = "https://api.bithumb.com/public/ticker/all";
    public static final String MEXC_ALL_COIN = "https://www.mexc.com/open/api/v2/market/ticker";
    public static final String UPBIT_ALL_COIN = "https://api.upbit.com/v1/market/all";
    public static final String BINANCE_ALL_COIN = "https://api.binance.com/api/v3/exchangeInfo";
    public static final String HUOBI_ALL_COIN = "https://api-cloud.huobi.co.kr/v1/common/symbols";
    public static final String GATEIO_ALL_COIN = "https://api.gateio.ws/api/v4/spot/currency_pairs";

    // 각 코인 상세 조회
    public static String COINONE_COIN_DETAIL = "https://api.coinone.co.kr/ticker";
    public static String BITHUMB_COIN_DETAIL = "https://api.bithumb.com/public/ticker/";
    public static String MEXC_COIN_DETAIL = "https://www.mexc.com/open/api/v2/market/ticker";
    public static String UPBIT_COIN_DETAIL = "https://api.upbit.com/v1/ticker";
    public static String BINANCE_COIN_DETAIL = "https://api.binance.com/api/v3/ticker/24hr";
    public static String HUOBI_COIN_DETAIL = "https://api-cloud.huobi.co.kr/market/detail";
    public static String GATEIO_COIN_DETAIL = "";
}
