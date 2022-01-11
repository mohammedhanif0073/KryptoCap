package com.example.cryptotokenbaba.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotokenbaba.Interface.ILoadMore;
import com.example.cryptotokenbaba.Model.CoinModel;
import com.example.cryptotokenbaba.Model.NewsModel;
import com.example.cryptotokenbaba.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ILoadMore iLoadMore;
    int visibleTheshold = 5,lastVisibleItem, totalItemCount;
    boolean isLoading;
    Context context;
    List<NewsModel> items;
    public NewsAdapter(RecyclerView recyclerView, Context context, List<NewsModel> items) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        NewsModel item = items.get(position);
        NewsViewHolder holderItem1 = (NewsViewHolder)holder1;

        Log.d("RECYCLEEEEEEEEEEimage",""+item.getImage("image"));

        Picasso.with(context).load(item.getImage("image")).into(holderItem1.image);
        Log.d("RECYCLEEEEEEEEEEimage",""+item.getImage("image"));

        holderItem1.category.setText(item.getCategory("category"));

        holderItem1.headline.setText(item.getHeadline("headline"));

        Log.d("category",""+item.getCategory("category"));

        holderItem1.summary.setText(item.getSummary("summary"));
        Log.d("summary",""+item.getSummary("summary"));

        holderItem1.url.setText(item.getUrl("url"));
        Log.d("url",""+item.getUrl("url"));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setLoaded(){
        isLoading = true;
    }
    public void updateData(List<NewsModel> newsModels){
        this.items = newsModels;
        notifyDataSetChanged();
    }
}

