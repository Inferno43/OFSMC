package com.ofs.ofmc.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.EmployeeSeatingModel;
import com.ofs.ofmc.model.Zones;
import com.ofs.ofmc.toolbox.Constants;

import java.util.ArrayList;
import java.util.List;

import by.anatoldeveloper.hallscheme.hall.HallScheme;
import by.anatoldeveloper.hallscheme.hall.Seat;
import by.anatoldeveloper.hallscheme.hall.SeatListener;
import by.anatoldeveloper.hallscheme.hall.Zone;
import by.anatoldeveloper.hallscheme.hall.ZoneListener;
import by.anatoldeveloper.hallscheme.view.ZoomableImageView;

/**
 * Created by saravana.subramanian on 2/28/17.
 */

public class EmployeeSeatingView extends BaseFragment implements HomeContract.ViewEmployeeSeating{
    HomeContract.PresenterEmployeeSeating presenter;
    View rootView;
    ZoomableImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.employee_seating_view_stub,container,false);
        imageView = (ZoomableImageView) rootView.findViewById(R.id.zoomable_image);
        HallScheme scheme = new HallScheme(imageView,  basicScheme(), getActivity());

        scheme.setSeatListener(new SeatListener() {

            @Override
            public void selectSeat(int id) {
                Toast.makeText(getActivity(), "select seat " + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unSelectSeat(int id) {
                Toast.makeText(getActivity(), "unSelect seat " + id, Toast.LENGTH_SHORT).show();
            }

        });
//        scheme.setZones(zones());
//        scheme.setZoneListener(new ZoneListener() {
//            @Override
//            public void zoneClick(int id) {
//                Toast.makeText(getActivity(), "Zone click " + id, Toast.LENGTH_SHORT).show();
//            }
//        });

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
    public void setPresenter(HomeContract.PresenterEmployeeSeating presenter) {
        this.presenter = presenter;
    }

    public Seat[][] basicScheme() {
        Seat seats[][] = new Seat[18][11];
        for (int i = 0; i < 18; i++)
            for(int j = 0; j < 11; j++) {
                EmployeeSeatingModel seat = new EmployeeSeatingModel();
                seat.id = i * 11 + (j+1);
                seat.selectedSeatMarker = String.valueOf(j+1);

                if((i>=4 && (j==0||j==1 )) || (i==7 && (j==2 || j==3)))  seat.status = HallScheme.SeatStatus.EMPTY;
                else if(j==4) seat.status = HallScheme.SeatStatus.EMPTY;
                else if(j==6 && (i==3 ||i==6 || i== 9 || i==12 || i==15))seat.status = HallScheme.SeatStatus.EMPTY;
                else seat.status = HallScheme.SeatStatus.FREE;

                seats[i][j] = seat;
            }

        return seats;
    }


    public List<Zone> zones() {
        List<Zone> zones = new ArrayList<>();
        Zones zone1 = new Zones(1, 8, 17, 10, 6, getActivity().getResources().getColor(R.color.dark_green), "Not used in current version");
        Zones zone2 = new Zones(2, 8, 4, 10, 12, Color.DKGRAY, "Not used in current version");
        Zones zone3 = new Zones(3, 0, 0, 26, 2, getActivity().getResources().getColor(R.color.dark_purple), "Not used in current version");

        zones.add(zone1);
        zones.add(zone2);
        zones.add(zone3);
        return zones;
    }
}
