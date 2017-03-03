package com.ofs.ofmc.contracts;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by saravana.subramanian on 3/3/17.
 */

public interface AbstractAdapter {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    void onBindViewHolder(final RecyclerView.ViewHolder holder,Object object, final int position);

}
