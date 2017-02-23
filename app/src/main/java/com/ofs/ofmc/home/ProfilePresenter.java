package com.ofs.ofmc.home;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ofs.ofmc.model.Employee;
import com.ofs.ofmc.toolbox.SharedPref;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class ProfilePresenter implements HomeContract.PresenterProfile{

    HomeContract.ViewProfile viewProfile;
    FirebaseDatabase firebaseDatabase;
    Employee employee;
    Context context;


    public ProfilePresenter(HomeContract.ViewProfile viewProfile,FirebaseDatabase firebaseDatabase) {
        this.viewProfile = viewProfile;
        this.firebaseDatabase = firebaseDatabase;
        this.viewProfile.setPresenter(this);
    }

    @Override
    public void loadProfile(final String userId) {
        firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               if(dataSnapshot.child("Employees").hasChild(userId)) {
                   String user = (String) dataSnapshot.child("Employees").child(userId).child("userId").getValue();
                   String employeeId = (String) dataSnapshot.child("Employees").child(userId).child("employeeId").getValue();
                   String employeeName = (String) dataSnapshot.child("Employees").child(userId).child("employeeName").getValue();
                   String employeeDepartment = (String) dataSnapshot.child("Employees").child(userId).child("employeeDepartment").getValue();
                   String employeeEmail = (String) dataSnapshot.child("Employees").child(userId).child("employeeEmail").getValue();
                   String employeePhase = (String) dataSnapshot.child("Employees").child(userId).child("employeePhase").getValue();
                   String employeeExtension = (String) dataSnapshot.child("Employees").child(userId).child("employeeExtension").getValue();

                       viewProfile.showProfile(new Employee(user,employeeId,employeeName,employeeDepartment,"",
                               employeeEmail,employeePhase,employeeExtension));
                       viewProfile.showMessage("your profile loaded");

               }else{
                   viewProfile.showMessage("your profile could not be loaded");
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                viewProfile.showMessage("Update your profile");
            }
        });



    }

    @Override
    public void editProfile(Employee employee) {
        Map<String,Object> emp = new HashMap<>();
        emp.put(employee.getUserId(),employee);
        firebaseDatabase.getReference().child("Employees").updateChildren(emp);
    }

    @Override
    public void saveProfile(final Employee employee) {
        firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Employees").hasChild(employee.getUserId()))
                    firebaseDatabase.getReference().child("Employees").child(employee.getUserId()).setValue(employee);
                else
                    editProfile(employee);

                profileCompleted(true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                viewProfile.showMessage("Update your profile");
            }
        });
    }

    @Override
    public void profileCompleted(boolean complete) {
        SharedPref sharedPref = new SharedPref();
        sharedPref.save(context,SharedPref.PREFS_IS_PROFILE_COMPLETE,complete);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void start() {

    }
}
