package com.ofs.ofmc.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.Holidays;
import com.ofs.ofmc.toolbox.Constants;
import com.ofs.ofmc.toolbox.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class HolidaysView extends BaseFragment implements HomeContract.ViewHolidays {

    HomeContract.PresenterHolidays presenter;
    private View rootView;
    private RecyclerView holidayList;
    private RecyclerView.LayoutManager mLayoutManager;
    private HolidayAdapter mAdapter;
    private ArrayList<Holidays> list;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.holidays,container,false);
        context = getActivity();

        getActivity().setTitle(getString(R.string.ofs_holiday));
        holidayList = (RecyclerView) rootView.findViewById(R.id.holidaysList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        holidayList.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        list.add(new Holidays("26-Jan-2017","Thursday","Republic Day",false));
        list.add(new Holidays("14-Apr-2017","Friday","Good Friday",false));
        list.add(new Holidays("1-May-2017","Monday","May Day",false));
        list.add(new Holidays("26-Jun-2017","Monday","Ramazan",true));
        list.add(new Holidays("15-Aug-2017","Tuesday","Independence Day",false));
        list.add(new Holidays("25-Aug-2017","Friday","Vinayagar  Chaturthi",false));
        list.add(new Holidays("29-Sep-2017","Friday","Ayudha Pooja",false));
        list.add(new Holidays("2-Oct-2017","Monday","Gandhi Jayanthi",false));
        list.add(new Holidays("18-Oct-2017","Wednesday","Deepavali",false));
        list.add(new Holidays("25-Dec-2017","Monday","Christmas Day",false));

        mAdapter = new HolidayAdapter(getActivity(),list);
        holidayList.setItemAnimator(new DefaultItemAnimator());
        holidayList.addItemDecoration(new DividerItemDecoration(context,R.drawable.divider));
        holidayList.setAdapter(mAdapter);

        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt(Constants.MOTION_X_ARG);
                int cy = getArguments().getInt(Constants.MOTION_Y_ARG);
                float finalRadius   = (int) Math.hypot(left, bottom);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
                anim.setDuration(375);
                anim.start();
            }
        });

        return rootView;
    }

    @Override
    public void setPresenter(HomeContract.PresenterHolidays presenter) {
        this.presenter = presenter;
    }
}
