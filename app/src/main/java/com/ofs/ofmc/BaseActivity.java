package com.ofs.ofmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.ofs.ofmc.abstracts.AbstractActivityCallback;
import com.ofs.ofmc.abstracts.AbstractFragmentCallback;
import com.ofs.ofmc.abstracts.Arguments;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class BaseActivity extends AppCompatActivity implements AbstractFragmentCallback,AbstractActivityCallback{
    protected FirebaseDatabase firebaseDatabase;
    protected Bundle bundleArgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void startActivity(Class<? extends AppCompatActivity> activity, Bundle args) {
        Intent intent = new Intent(this, activity);
        if (null != args) intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, boolean addToBackstack, Bundle args) {
        try{
            if(fragment.isAdded()){
                return; //or return false/true, based on where you are calling from
            }
            String backStateName = fragment.getClass().getName();
            try{
                if (args != null && !fragment.isAdded()) fragment.setArguments(args);
            }catch (Exception e){
                e.printStackTrace();
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.contentFrame, fragment, backStateName);
            if (addToBackstack) transaction.addToBackStack(backStateName);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
