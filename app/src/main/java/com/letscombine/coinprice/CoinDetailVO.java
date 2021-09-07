package com.letscombine.coinprice;

public class CoinDetailVO {

    private String coinExchange;
    private String coinName;
    private String coinPrice;
    private String coinVolume;


    public CoinDetailVO(String coinExchange, String coinName, String coinPrice, String coinVolume) {
        this.coinExchange = coinExchange;
        this.coinName = coinName;
        this.coinPrice = coinPrice;
        this.coinVolume = coinVolume;
    }

    public String getCoinExchange() {
        return coinExchange;
    }

    public void setCoinExchange(String coinExchange) {
        this.coinExchange = coinExchange;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(String coinPrice) {
        this.coinPrice = coinPrice;
    }

    public String getCoinVolume() {
        return coinVolume;
    }

    public void setCoinVolume(String coinVolume) {
        this.coinVolume = coinVolume;
    }
}
