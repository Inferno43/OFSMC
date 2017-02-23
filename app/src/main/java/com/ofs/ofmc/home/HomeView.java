package com.ofs.ofmc.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.Employee;
import com.ofs.ofmc.toolbox.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class HomeView extends BaseFragment  implements HomeContract.ViewHome{


    HomeContract.PresenterHome presenterHome;

    private RecyclerView employeeList;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeAdapter mAdapter;
    private ArrayList<Employee> list;
    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home,container,false);

        employeeList = (RecyclerView) rootView.findViewById(R.id.employeeList);
        //message = (TextView)mRootView.findViewById(R.id.message);
        mLayoutManager = new LinearLayoutManager(getActivity());
        employeeList.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.child("Employees").getChildren()) {

                    Employee employee = postSnapshot.getValue(Employee.class);
                    list.add(employee);
                    mAdapter = new HomeAdapter(list, new ClickListener() {
                        @Override
                        public void onItemClicked(String empId) {
                            Home home = (Home) getActivity();
                            home.setFragmentProfile(true,empId);
                        }
                    });
                    employeeList.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt(Constants.MOTION_X_ARG);
                int cy = getArguments().getInt(Constants.MOTION_Y_ARG);
                float finalRadius   = (int) Math.hypot(left, bottom);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
                anim.setDuration(1000);
                anim.start();
            }
        });
        return rootView;
    }

    @Override
    public void setPresenter(HomeContract.PresenterHome presenter) {
        presenterHome = presenter;
    }


    public interface ClickListener{
        void onItemClicked(String empId);
    }
}
