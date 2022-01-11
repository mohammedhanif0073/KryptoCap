package com.example.cryptotokenbaba.Adapter;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotokenbaba.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CoinViewHolder extends RecyclerView.ViewHolder {
    public ImageView coin_logo_url;
    public TextView coin_rank,coin_symbol, coin_name, coin_price,coin_price_change,coin_market_cap,high_24,low_24;


    public CoinViewHolder(View itemView){
        super(itemView);
        coin_rank = (TextView) itemView.findViewById(R.id.rank);
        coin_logo_url = (ImageView) itemView.findViewById(R.id.coin_icon);
        coin_symbol = (TextView) itemView.findViewById(R.id.coin_symbol);
        coin_name = (TextView) itemView.findViewById(R.id.coin_name);
        coin_price = (TextView) itemView.findViewById(R.id.priceInrText);
        coin_price_change = (TextView) itemView.findViewById(R.id.price_change);
        coin_market_cap = (TextView) itemView.findViewById(R.id.market_cap);
        high_24 = (TextView) itemView.findViewById(R.id.high_24);
        low_24 = (TextView)itemView.findViewById(R.id.low_24);


    }

}
