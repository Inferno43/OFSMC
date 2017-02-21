package com.ofs.ofmc.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.database.FirebaseDatabase;
import com.ofs.ofmc.BaseActivity;
import com.ofs.ofmc.R;
import com.ofs.ofmc.toolbox.SharedPref;

public class Home extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    FirebaseDatabase firebaseDatabase;
    ProfilePresenter profilePresenter;
    ProfileView profileView;
    HomePresenter homePresenter;
    HomeView homeView;
    SharedPref sharedPref;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_base);
        context = this;
        sharedPref = new SharedPref();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        mDrawerLayout.addDrawerListener(toggle);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);


        profileView = new ProfileView();
        homeView = new HomeView();

        profilePresenter = new ProfilePresenter(profileView,firebaseDatabase);


        if(!sharedPref.getBoolean(context,SharedPref.PREFS_IS_PROFILE_COMPLETE)){
            replaceFragment(getSupportFragmentManager(),profileView,false,null);
        }else{
            replaceFragment(getSupportFragmentManager(),homeView,false,null);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                replaceFragment(getSupportFragmentManager(),profileView,false,null);
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_logout:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}
