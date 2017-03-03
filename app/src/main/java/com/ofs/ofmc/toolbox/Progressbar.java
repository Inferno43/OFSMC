package com.ofs.ofmc.toolbox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.R;

/**
 * Created by saravana.subramanian on 3/3/17.
 */

public class Progressbar extends DialogFragment {
    View view;
    MetaballView metaballView;

    int style;
    int theme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        style = DialogFragment.STYLE_NO_FRAME;
        theme = android.R.style.Theme_Holo_Light;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.progress, container, false);
        metaballView = (MetaballView) view.findViewById(R.id.metaball);
        metaballView.setPaintMode(1);
        return view;
    }


}
