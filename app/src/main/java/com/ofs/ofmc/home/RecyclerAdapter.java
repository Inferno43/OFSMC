package com.ofs.ofmc.home;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ofs.ofmc.contracts.AbstractAdapter;
import com.ofs.ofmc.contracts.AbstractListItemOnclick;

import java.util.ArrayList;

/**
 * Created by saravana.subramanian on 3/3/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList arrayList;
    AbstractAdapter adapter;
    AbstractListItemOnclick itemOnclick;
    public RecyclerAdapter(ArrayList arrayList, AbstractAdapter adapter,AbstractListItemOnclick itemOnclick) {
        this.arrayList = arrayList;
        this.adapter = adapter;
        this.itemOnclick = itemOnclick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapter.onBindViewHolder(holder,arrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
