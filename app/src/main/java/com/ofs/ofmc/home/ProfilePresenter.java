package com.ofs.ofmc.home;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ofs.ofmc.model.Employee;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class ProfilePresenter implements HomeContract.PresenterProfile{

    HomeContract.ViewProfile viewProfile;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    Employee employee;


    public ProfilePresenter(HomeContract.ViewProfile viewProfile,FirebaseDatabase firebaseDatabase) {
        this.viewProfile = viewProfile;
        this.firebaseDatabase = firebaseDatabase;
        this.viewProfile.setPresenter(this);
        mDatabase = firebaseDatabase.getReference();
    }

    @Override
    public void loadProfile(final String userId) {

        firebaseDatabase.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot empl: dataSnapshot.child("Employees").getChildren()) {
                    String user = (String) empl.child("userId").getValue();
                    String employeeId = (String) empl.child("employeeId").getValue();
                    String employeeName = (String) empl.child("employeeName").getValue();
                    String employeeDepartment = (String) empl.child("employeeDepartment").getValue();
                    String employeeEmail = (String) empl.child("employeeEmail").getValue();
                    String employeePhase = (String) empl.child("employeePhase").getValue();
                    String employeeExtension = (String) empl.child("employeeExtension").getValue();
                    if(userId.equals(user)){
                        employee = new Employee(user,employeeId,employeeName,employeeDepartment,"",
                                employeeEmail,employeePhase,employeeExtension);
                        if(employee!=null){
                            viewProfile.showProfile(employee);
                            viewProfile.showMessage("your profile loaded");

                        }else{
                            viewProfile.showMessage("profile could not be loaded");

                        }

                    }
                }


                //employee = new Employee(emp.child())
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                viewProfile.showMessage("Update your profile");
            }
        });



    }

    @Override
    public void editProfile() {

    }

    @Override
    public void saveProfile(final Employee employee) {
        firebaseDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.getDatabase().getReference().child("Employees").push().setValue(employee);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                viewProfile.showMessage("Update your profile");
            }
        });
    }

    @Override
    public void start() {

    }
}
