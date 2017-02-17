package com.ofs.ofmc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ofs.ofmc.abstracts.AbstractActivityCallback;
import com.ofs.ofmc.abstracts.AbstractFragmentCallback;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class BaseFragment extends Fragment implements AbstractActivityCallback,AbstractFragmentCallback{

    private AbstractFragmentCallback mFragmentCallback;
    private AbstractActivityCallback mActivityCallback;

    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected FirebaseUser user;
    protected FirebaseDatabase firebaseDatabase;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                }else {

                }
            }
        };
        try {
            mFragmentCallback = (AbstractFragmentCallback) activity;
            mActivityCallback = (AbstractActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + AbstractFragmentCallback.class.getCanonicalName());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void startActivity(Class<? extends AppCompatActivity> activity, Bundle args) {
        mActivityCallback.startActivity(activity,args);
    }

    @Override
    public void replaceFragment(FragmentManager fragmentManager,Fragment fragment, boolean addToBackstack, Bundle args) {
        mFragmentCallback.replaceFragment(fragmentManager,fragment, addToBackstack,args);
    }
}
