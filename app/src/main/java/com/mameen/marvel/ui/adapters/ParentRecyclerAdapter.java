package com.mameen.marvel.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mameen.marvel.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * is a base class to extend from it the recyclerview adapter
 */
abstract class ParentRecyclerAdapter<Item> extends RecyclerView.Adapter<ParentRecyclerViewHolder> {
    Context context;
    List<Item> data;
    int layoutId;
    OnItemClickListener itemClickListener;

    ParentRecyclerAdapter(Context context, List<Item> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    ParentRecyclerAdapter(Context context, Item[] data, int layoutId) {
        this.context = context;
        this.data = Arrays.asList(data);
        this.layoutId = layoutId;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void InsertAll(List<Item> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void Insert(int position, Item item) {
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void Delete(int postion) {
        data.remove(postion);
        notifyDataSetChanged();
    }

    public void update(int position, Item item) {
        data.remove(position);
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void updateAll(List<Item> items) {
        data = new ArrayList<>();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public List<Item> getData() {
        return data;
    }


}
