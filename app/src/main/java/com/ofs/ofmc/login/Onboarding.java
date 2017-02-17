package com.ofs.ofmc.login;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ofs.ofmc.BaseActivity;
import com.ofs.ofmc.R;
import com.ofs.ofmc.abstracts.Arguments;
import com.ofs.ofmc.toolbox.Constants;

public class Onboarding extends BaseActivity implements Arguments{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;

    LoginPresenter loginPresenter;
    SignUpPresenter signUpPresenter;
    LoginView loginView;
    SignUpView signUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        loginView = new LoginView();
        signUpView = new SignUpView();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        loginPresenter = new LoginPresenter(loginView);
        signUpPresenter = new SignUpPresenter(signUpView);

        loginPresenter.start();
        signUpPresenter.start();


    }

    @Override
    public void setBundle(Bundle args) {
        if(args!=null){
            loginView.setBundle(args);
        }

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return loginView != null ? loginView : new LoginView();
                case 1:

                    return signUpView != null ? signUpView : new SignUpView();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SIGN IN";
                case 1:
                    return "SIGN UP";
            }
            return null;
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }


}
