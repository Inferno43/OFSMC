package com.ofs.ofmc.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.Employee;
import com.ofs.ofmc.toolbox.Constants;
import com.ofs.ofmc.toolbox.SharedPref;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class ProfileView extends BaseFragment implements HomeContract.ViewProfile {

    HomeContract.PresenterProfile presenter;
    private View rootView;
    private String userId;
    private EditText UserName;
    private EditText Email;
    private EditText EmployeeId;
    private EditText Department;
    private EditText Location;
    private EditText Extension;
    private Button   submit;
    Employee employee;
    Context context;
    SharedPref sharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile,container,false);
        sharedPref = new SharedPref();
        context = getActivity();
        UserName = (EditText) rootView.findViewById(R.id.userName);
        Email = (EditText) rootView.findViewById(R.id.email);
        EmployeeId = (EditText) rootView.findViewById(R.id.emp_id);
        Department = (EditText) rootView.findViewById(R.id.department);
        Location = (EditText) rootView.findViewById(R.id.phase);
        Extension = (EditText) rootView.findViewById(R.id.extension);
        submit = (Button) rootView.findViewById(R.id.register);

        userId = getArguments().getString(Constants.EXTRA_USERID)!= null ?
                getArguments().getString(Constants.EXTRA_USERID):sharedPref.getString(context,SharedPref.PREFS_USERID);
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt(Constants.MOTION_X_ARG);
                int cy = getArguments().getInt(Constants.MOTION_Y_ARG);
                float finalRadius   = (int) Math.hypot(left, bottom);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
                anim.setDuration(500);
                anim.start();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.setContext(context);
        presenter.loadProfile(userId);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee = new Employee(sharedPref.getString(context,SharedPref.PREFS_USERID),
                        EmployeeId.getText().toString(),UserName.getText().toString(),Department.getText().toString(),
                        "",Email.getText().toString(),Location.getText().toString(),Extension.getText().toString());
                presenter.saveProfile(employee);
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProfile(Employee employee) {
        UserName.setText(employee.getEmployeeName());
        Email.setText(employee.getEmployeeEmail());
        EmployeeId.setText(employee.getEmployeeId());
        Department.setText(employee.getEmployeeDepartment());
        Location.setText(employee.getEmployeePhase());
        Extension.setText(employee.getEmployeeExtension());

    }


    @Override
    public void setPresenter(HomeContract.PresenterProfile presenter) {
        this.presenter = presenter;
    }


}
