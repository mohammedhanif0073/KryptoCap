package com.example.cryptotokenbaba.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotokenbaba.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView category,headline,summary,url;
    public NewsViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.image);
        category = (TextView) itemView.findViewById(R.id.category);
        headline = (TextView) itemView.findViewById(R.id.headline);
        summary = (TextView) itemView.findViewById(R.id.summary);
        url = (TextView)itemView.findViewById(R.id.url);
    }

}
