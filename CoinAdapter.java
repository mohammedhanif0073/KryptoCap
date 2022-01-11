package com.example.cryptotokenbaba.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.cryptotokenbaba.Interface.ILoadMore;
import com.example.cryptotokenbaba.Model.CoinModel;
import com.example.cryptotokenbaba.R;
import com.example.cryptotokenbaba.Util.Utils;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ILoadMore iLoadMore;
    boolean isLoading;

    Context context;
    List<CoinModel> items;
    int visibleTheshold = 5,lastVisibleItem, totalItemCount;

    DecimalFormat df = new DecimalFormat("#.##");
    public CoinAdapter(RecyclerView recyclerView,Context context,List<CoinModel> items) {
        this.context = context;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount= linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= lastVisibleItem+visibleTheshold){
                    if(iLoadMore!=null)
                        iLoadMore.onLoadMore();
                    isLoading=true;
                }
            }
        });
    }

    public void setiLoadMore(ILoadMore iLoadMore){
        this.iLoadMore = iLoadMore;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_layout,parent,false);
        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CoinModel item = items.get(position);
        CoinViewHolder holderItem = (CoinViewHolder)holder;

        holderItem.coin_rank.setText(item.getMarket_cap_rank("market_cap_rank"));
        Log.d("RECYCLEEEEEEEEEERank",""+item.getMarket_cap_rank("market_cap_rank"));

     /*   Glide.with(mContext)
                .asBitmap()
                .load("https://i2.wp.com/www.siasat.com/wp-content/uploads/2018/03/Rosamund-Pike.jpeg?fit=600%2C421&ssl=1")
                .into(ivProfileImage);*/
       // Glide.with(context).asBitmap().load(item.getLogo_url("image")).into(holderItem.coin_logo_url);

        Picasso.with(context).load(item.getImage("image")).into(holderItem.coin_logo_url);

     // Glide.with(context).asBitmap().load(item.getLogo_url("logo_url")).diskCacheStrategy(DiskCacheStrategy.NONE).into(holderItem.coin_logo_url);
      // Utils.fetchSvg(context,item.getLogo_url("logo_url"),holderItem.coin_logo_url);

       //Utils.fetchSvg(context,item.getLogo_url("logo_url"),holderItem.coin_logo_url);

////////////////////////IMAGE WITH ERROR PRINT//////////////////////////////
/*
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
        });

        builder.build().load(item.getLogo_url("logo_url")).into(holderItem.coin_logo_url, new com.squareup.picasso.Callback()
        {
            @Override
            public void onSuccess()
            {
                Log.d("TAG", "onsuccess");
               // loadView.setVisibility(View.GONE);
                holderItem.coin_logo_url.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError()
            {
                Log.d("TAG", "onerror");
                holderItem.coin_logo_url.setImageResource(R.drawable.ether);
            }
        });
*/



        Log.d("RECYCLEEEEEEEEEEimage",""+item.getImage("image"));


        holderItem.coin_name.setText(item.getName("name"));
        Log.d("RECYCLEEEEEEEEEEName",""+item.getName("name"));

        holderItem.coin_symbol.setText(item.getSymbol("symbol").toUpperCase());

        Log.d("RECYCLEEEEEEEEEESymbol",""+item.getSymbol("symbol").toUpperCase());

        holderItem.coin_price.setText(item.getCurrent_price("current_price"));//+"₹"/ALT+CNRTL+4
        Log.d("RECYCLEEEEEEEEEEPrice",""+item.getCurrent_price("current_price"));

        //  time = Double.valueOf(df.format(item.getPrice_change_24h("price_change_24h")));
      //  holderItem.coin_price_change.setText(item.getPrice_change_percentage_24h("price_change_percentage_24h"));
        ///////////////////////////////////////pricechange/////////////////////////
        holderItem.coin_price_change.setText(item.getPrice_change_percentage_24h("price_change_percentage_24h"));
        holderItem.coin_price_change.append("%");

        holderItem.coin_price_change.setTextColor(item.getPrice_change_percentage_24h("price_change_percentage_24h").contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));




        Log.d("RECYCLEEEEPricechange",""+item.getPrice_change_percentage_24h("price_change_percentage_24h"));

        holderItem.coin_market_cap.setText(item.getMarket_cap("market_cap"));
        holderItem.high_24.setText(item.getHigh_24h("high_24h"));
        Log.d("RECYCLEEEEHigh",""+item.getHigh_24h("high_24h")+"₹");


        holderItem.low_24.setText(item.getLow_24h("low_24h"));
        Log.d("RECYCLEEEELow",""+item.getLow_24h("low_24h")+"₹");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setLoaded(){
        isLoading = true;
    }
    public void updateData(List<CoinModel> coinModels){
        this.items = coinModels;
        notifyDataSetChanged();
    }
}

