package com.example.cryptotokenbaba.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cryptotokenbaba.Adapter.CoinAdapter;
import com.example.cryptotokenbaba.Adapter.NewsAdapter;
import com.example.cryptotokenbaba.Interface.ILoadMore;
import com.example.cryptotokenbaba.Model.CoinModel;
import com.example.cryptotokenbaba.Model.NewsModel;
import com.example.cryptotokenbaba.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class News extends AppCompatActivity {
    String apiUrl ="https://finnhub.io/api/v1/news?category=general&token=c5hhmpaad3iep34ptl90";
           // "https://newsapi.org/v2/top-headlines/sources?apiKey=d41e638e43bf476b85b151a97a8e25c5";
            //"https://newsapi.org/v2/everything?q=tesla&from=2021-09-09&sortBy=publishedAt&apiKey=d41e638e43bf476b85b151a97a8e25c5";
            //"https://cryptopanic.com/api/v1/posts/?auth_token=126d2f72ac410e85d75cd7236a0fdb8306e2aa9d&regions=en,de";
            //"https://cryptopanic.com/api/v1/posts/?auth_token=126d2f72ac410e85d75cd7236a0fdb8306e2aa9d&kind=news";

    JSONObject ob;
    JSONArray ja;
    List<NewsModel> items = new ArrayList<>();
    NewsAdapter adapter;
    RecyclerView recyclerView;
    OkHttpClient client;
    Request request;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);


        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadFirst10coin(0);
                //updateFieldOrds();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                loadFirst10coin(0);
                setupAdapter();
             //   updateFieldOrds();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.newslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
      //  updateFieldOrds();
    }
    private void setupAdapter() {
        adapter = new NewsAdapter(recyclerView,News.this,items);
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
                    Toast.makeText(News.this,"Max items is 1000",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void loadNext10coin(int index) {
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format(apiUrl,index)).build();
      /*  Request request = new Request.Builder()
                .url("https://crypto-news5.p.rapidapi.com/")
                .get()
                .addHeader("x-rapidapi-host", "crypto-news5.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "c620407be4mshd67ba47b6bac259p19ec8fjsnbd2872bdf330")
                .build();*/
        Log.d("Newsapiiiiiiiiii",""+request);
        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(News.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String body = response.body().string();
                        Gson gson = new Gson();
                        final List<NewsModel> newItems = gson.fromJson(body,new TypeToken<List<NewsModel>>(){}.getType());
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

        Log.d("Newsapiiiiiiiiii",""+request);
        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(News.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d("RRRRRRResponse", "" + response);
                        String body = response.body().string();
                        Log.d("NEWwwwwwwbody", body);
                        Gson gson = new Gson();
                        final List<NewsModel> newItems = gson.fromJson(body, new TypeToken<List<NewsModel>>() {
                        }.getType());

                        Log.d("NEWITEM............", "" + gson);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                adapter.updateData(newItems);

                            }
                        });
/*                        try {
                           // Log.d("Updatessssss", ""+body);
                            ob = new JSONObject(body);
                            Log.d("ARRAYYYYYYY", ""+ob);

                            ja = ob.getJSONArray("sources");
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject f = ja.getJSONObject(i);
                                Log.d("Updatessssss", "" + f);
                                //  f.put("title", i);
                               // String am = "title";
                                JSONArray ap = f.getJSONArray("name");
                                for (int j = 0; j < ja.length(); j++) {
                                    JSONObject s = ap.getJSONObject(j);
                                   // String body = s.body().string();

                                    Log.d("Updatessssssfff", "" + ap);
                                    Gson gson = new Gson();
                                    final List<NewsModel> newItems = gson.fromJson(body, new TypeToken<List<NewsModel>>() {
                                    }.getType());
                                    Log.d("NEWITEM............", "" + newItems);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            adapter.updateData(newItems);

                                        }
                                    });

                                }
                           }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }*/
                    }
                });


                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }

/*    public void updateFieldOrds() {


        try {
            Log.d("Updatessssss", ""+apiUrl);
             ob = new JSONObject(apiUrl.toString());
            Log.d("Updatessssss", ""+ob);
            ja = ob.getJSONArray("articles");
            Log.d("Updatessssss", ""+ja);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject f = ja.getJSONObject(i);
                Log.d("Updatessssss", ""+f);
              //  f.put("title", i);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }*/


}


