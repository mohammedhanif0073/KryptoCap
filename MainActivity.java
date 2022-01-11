package com.example.cryptotokenbaba.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



import com.example.cryptotokenbaba.Adapter.CoinAdapter;
import com.example.cryptotokenbaba.Interface.ILoadMore;
import com.example.cryptotokenbaba.Model.CoinModel;
import com.example.cryptotokenbaba.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    List<CoinModel> items = new ArrayList<>();
    CoinAdapter adapter;
    RecyclerView recyclerView;
    OkHttpClient client;
    Request request;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    String apiUrl ="https://api.coingecko.com/api/v3/coins/markets?vs_currency=inr&ids";
            //"https://api.nomics.com/v1/currencies/ticker?key=0e95a9cb09edf75b360ff8fc8bc192a86b2f9b07&ids&interval=1h,1d,7d&convert=INR&per-page=100&page=1";
            //"https://api.nomics.com/v1/currencies/ticker?key=0e95a9cb09edf75b360ff8fc8bc192a86b2f9b07&ids=BTC&interval=1h,1d,7d&convert=INR&per-page=100&page=1";

    //"https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=8f86ed53-fdd9-4901-8193-5f0464ae26bf&sort=market_cap&start=1&limit=1&cryptocurrency_type=tokens&convert=INR";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView image = (ImageView)findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), News.class);
                startActivity(intent);
            }
        });

  /*      @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }*/



        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.rootLayout);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadFirst10coin(0);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                loadFirst10coin(0);
                setupAdapter();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.coinList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
    }



    private void setupAdapter() {
        adapter = new CoinAdapter(recyclerView,MainActivity.this,items);
        recyclerView.setAdapter(adapter);
        adapter.setiLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(items.size() <= 1000)//Max size is 1000 coin
                {
                    loadNext10coin(items.size());
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Max items is 1000",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void loadNext10coin(int index) {
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format(apiUrl,index)).build();

        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String body = response.body().string();
                        Gson gson = new Gson();

                        final List<CoinModel> newItems = gson.fromJson(body,new TypeToken<List<CoinModel>>(){}.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                items.addAll(newItems);
                                adapter.setLoaded();
                                adapter.updateData(items);
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        });

                    }
                });
    }

    private void loadFirst10coin(int index) {
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format(apiUrl,index)).build();
        Log.d("api",apiUrl);
        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String body = response.body().string();
                        Log.d("apiiiiiiiiiiiii",body);
                        Gson gson = new Gson();
                        final List<CoinModel> newItems = gson.fromJson(body,new TypeToken<List<CoinModel>>(){}.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                adapter.updateData(newItems);

                            }
                        });

                    }
                });
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}