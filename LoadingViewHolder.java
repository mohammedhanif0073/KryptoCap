package com.example.cryptotokenbaba.Adapter;

import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotokenbaba.R;


public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;
    public LoadingViewHolder(View itemView)
    {
        super(itemView);
         progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar);
    }

    public void setVisibility(int gone) {
    }
}
