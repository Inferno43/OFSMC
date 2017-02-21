package com.ofs.ofmc.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.model.Employee;
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
        userId = sharedPref.getString(context,SharedPref.PREFS_USERID);
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


        return rootView;
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
