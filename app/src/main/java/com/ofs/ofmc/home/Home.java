package com.ofs.ofmc.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ofs.ofmc.BaseActivity;
import com.ofs.ofmc.R;
import com.ofs.ofmc.toolbox.Constants;
import com.ofs.ofmc.toolbox.SharedPref;

public class Home extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    FirebaseDatabase firebaseDatabase;

    ProfilePresenter profilePresenter;
    public ProfileView profileView;
    DirectoryPresenter directoryPresenter;
    public DirectoryView directoryView;
    HolidaysPresenter holidaysPresenter;
    public HolidaysView holidaysView;
    DashboardPresenter dashboardPresenter;
    public DashboardView dashboardView;
    EmployeeSeatingPresenter employeeSeatingPresenter;
    public EmployeeSeatingView employeeSeatingView;

    SharedPref sharedPref;
    Context context;
    Bundle fragmentArgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_base);
        context = this;
        sharedPref = new SharedPref();
        initFragmentArgs();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_back);
        toggle.setDrawerIndicatorEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        mDrawerLayout.addDrawerListener(toggle);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        profileView = new ProfileView();
        profileView.setArguments(fragmentArgs);

        directoryView = new DirectoryView();
        directoryView.setArguments(fragmentArgs);

        holidaysView = new HolidaysView();

        dashboardView = new DashboardView();

        employeeSeatingView = new EmployeeSeatingView();



        profilePresenter = new ProfilePresenter(profileView,firebaseDatabase);
        directoryPresenter = new DirectoryPresenter(directoryView);
        holidaysPresenter = new HolidaysPresenter(holidaysView);
        dashboardPresenter = new DashboardPresenter(dashboardView);
        employeeSeatingPresenter = new EmployeeSeatingPresenter(employeeSeatingView);


        if(firebaseDatabase.getReference().child("Employees").child(sharedPref.getString(context,SharedPref.PREFS_USERID)) == null
                && !sharedPref.getBoolean(context,SharedPref.PREFS_IS_PROFILE_COMPLETE) )
                {
            setFragmentProfile(false,null);
        }else{
            setFragmentDashboard(false);
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
                setFragmentDashboard(false);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_ofs_directory:
                setFragmentHome(false);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_ofs_holiday:
                setFragmentHolidays(false);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_profile:
                setFragmentProfile(false,null);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_seating:
                setFragmentSeating(false);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_logout:
                sharedPref.clearSharedPreference(context);
                FirebaseAuth.getInstance().signOut();
                finish();
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

    public void setFragmentProfile(boolean addToBackStack,String userId){
        fragmentArgs.putString(Constants.EXTRA_USERID,userId);
        replaceFragment(getSupportFragmentManager(),profileView,addToBackStack,fragmentArgs);
    }

    public void setFragmentHome(boolean addToBackStack){
        replaceFragment(getSupportFragmentManager(),directoryView,addToBackStack,fragmentArgs);
    }
    public void setFragmentHolidays(boolean addToBackStack){
        replaceFragment(getSupportFragmentManager(),holidaysView,addToBackStack,fragmentArgs);
    }
    public void setFragmentDashboard(boolean addToBackStack){
        replaceFragment(getSupportFragmentManager(),dashboardView,addToBackStack,fragmentArgs);
    }
    public void setFragmentSeating(boolean addToBackStack){
        replaceFragment(getSupportFragmentManager(),employeeSeatingView,addToBackStack,fragmentArgs);
    }



    void initFragmentArgs(){
        int centreX= getWindow().getDecorView().getWidth()  / 2;
        int centreY=getWindow().getDecorView().getWidth() / 2;
        fragmentArgs = new Bundle();
        fragmentArgs.putInt(Constants.MOTION_X_ARG,centreX);
        fragmentArgs.putInt(Constants.MOTION_Y_ARG,centreY);

    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()==0)
            new CustomDialog().show(getSupportFragmentManager(),null);
        else
            getSupportFragmentManager().popBackStackImmediate();


    }


    public static class CustomDialog extends DialogFragment {

        public static CustomDialog newInstance(int title) {
            CustomDialog frag = new CustomDialog();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Do you want to close the app?")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    startActivity(new Intent()
                                            . setAction(Intent.ACTION_MAIN)
                                            .addCategory(Intent.CATEGORY_HOME));
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();
        }
    }
}
