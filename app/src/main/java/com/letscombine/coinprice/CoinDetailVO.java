package com.letscombine.coinprice;

public class CoinDetailVO {

    private String coinExchange;
    private String coinName;
    private String coinPrice;
    private String coinTransactionAmount;


    public CoinDetailVO(String coinExchange, String coinName, String coinPrice, String coinTransactionAmount) {
        this.coinExchange = coinExchange;
        this.coinName = coinName;
        this.coinPrice = coinPrice;
        this.coinTransactionAmount = coinTransactionAmount;
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

    public String getCoinTransactionAmount() {
        return coinTransactionAmount;
    }

    public void setCoinTransactionAmount(String coinTransactionAmount) {
        this.coinTransactionAmount = coinTransactionAmount;
    }
}
