package com.example.splitit.app;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  abstract RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group);

}
