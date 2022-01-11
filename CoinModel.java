package com.example.cryptotokenbaba.Model;

public class CoinModel {
    public String market_cap_rank;
    public String image;
    public String symbol;
    public String name;
    public String current_price;
    public String price_change_percentage_24h;
    public String market_cap;
    public String high_24h;
    public String low_24h;


    public CoinModel(String market_cap_rank,String image, String symbol,String name, String current_price, String price_change_percentage_24h,
                     String market_cap,String high_24h,String low_24h)

    {
        this.market_cap_rank = market_cap_rank;
        this.image = image;
        this.symbol = symbol;
        this.name = name;
        this.current_price = current_price;
        this.price_change_percentage_24h = price_change_percentage_24h;
        this.market_cap = market_cap;
        this.high_24h = high_24h;
        this.low_24h = low_24h;

    }
    public String getMarket_cap_rank(String rank){
        return this.market_cap_rank;
    }
    public String getImage(String name) {

        return image;
    }
    public String getSymbol(String name)
    {
        return this.symbol;
    }
    public String getName(String name) {
        return this.name;
    }

    public String getCurrent_price(String name) {
        return this.current_price;
    }
    public String getPrice_change_percentage_24h(String name){
        return this.price_change_percentage_24h;
    }
    public String getMarket_cap(String name){
        return this.market_cap;
    }

    public String getHigh_24h(String high_24) {
        return this.high_24h;
    }
    public String getLow_24h(String low)
    {
        return this.low_24h;
    }
}
