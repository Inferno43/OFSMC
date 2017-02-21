package com.ofs.ofmc.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class HomeView extends BaseFragment  {


    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home,container,false);
        return rootView;
    }
}
