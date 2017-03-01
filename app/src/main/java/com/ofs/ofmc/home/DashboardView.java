package com.ofs.ofmc.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.exceptions.EmptyTextException;
import com.ofs.ofmc.model.DashboardModel;
import com.ofs.ofmc.toolbox.Constants;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import link.fls.swipestack.SwipeStack;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class DashboardView extends BaseFragment implements HomeContract.ViewDashboard,SwipeStack.SwipeStackListener {

    HomeContract.PresenterDashboard presenterDashboard;
    private Context context;
    private View rootView;
    private ViewPager viewPager;
    ArrayList<DashboardModel> dashboardModels;
    InkPageIndicator inkPageIndicator;
    SectionsPagerAdapter sectionsPagerAdapter;

    private SwipeStack mSwipeStack;
    private PagerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dashboard,container,false);
        context = getActivity();
        getActivity().setTitle(getString(R.string.ofs_home));
        dashboardModels = new ArrayList<>();
        //mSwipeStack = (SwipeStack) rootView.findViewById(R.id.swipeStack);
        viewPager = (ViewPager) rootView.findViewById(R.id.dashboardPager);
        dashboardModels.add(new DashboardModel("images/Mau4n7aCLtTfK9Ly2vWG9S1KBcD2.jpg","Saravana","Celebrating Birthday today"));
        dashboardModels.add(new DashboardModel("images/Mau4n7aCLtTfK9Ly2vWG9S1KBcD2.jpg","Saravana","Celebrating 3 Years service completion at OFS today"));
        dashboardModels.add(new DashboardModel("images/Mau4n7aCLtTfK9Ly2vWG9S1KBcD2.jpg","Saravana","Celebrating 3 Years service completion at OFS today"));

        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),dashboardModels);
        viewPager.setAdapter(sectionsPagerAdapter);
        try{
//            inkPageIndicator = (InkPageIndicator) rootView.findViewById(R.id.indicator);
//            inkPageIndicator.setViewPager(viewPager);
        }catch (Exception e){
            e.printStackTrace();
        }

        //mAdapter = new PagerAdapter(context,dashboardModels);
       // mSwipeStack.setAdapter(mAdapter);
        //mSwipeStack.setListener(this);

        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt(Constants.MOTION_X_ARG);
                int cy = getArguments().getInt(Constants.MOTION_Y_ARG);
                float finalRadius   = (int) Math.hypot(left, bottom);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
                anim.setDuration(375);
                anim.start();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(HomeContract.PresenterDashboard presenter) {
        presenterDashboard = presenter;
    }

    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends BaseFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static String ARG = "model";
        ImageView imageView;
        TextView title;
        TextView description;
        StorageReference storageReference ;
        private Context context;


        DashboardModel dashboardModel;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Parcelable parcelable) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARG, parcelable);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.dashboard_view_stub, container, false);
            context = getActivity();
            dashboardModel = getArguments().getParcelable(ARG);
            title = (TextView) rootView.findViewById(R.id.title);
            imageView =(ImageView)rootView.findViewById(R.id.imgView);
            description = (TextView)rootView.findViewById(R.id.description);
            storageReference = FirebaseStorage.getInstance().getReference();
            storageReference.child(dashboardModel.getImagePath())
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(context).load(uri.toString())
                            .placeholder(R.mipmap.ic_launcher)
                            .resize(100, 100).into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Animation fadeOut = new AlphaAnimation(0, 1);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setDuration(500);
                            imageView.startAnimation(fadeOut);

                        }

                        @Override
                        public void onError() {

                        }
                    });
                }});
            title.setText(dashboardModel.getTitle());
            description.setText(dashboardModel.getDescription());


            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<DashboardModel>dashboardModels;
        String title;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public SectionsPagerAdapter(FragmentManager fm,ArrayList<DashboardModel>dashboardModels) {
            super(fm);
            this.dashboardModels = dashboardModels;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(dashboardModels.get(position));


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return dashboardModels.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
                title = dashboardModels.get(position).getTitle();
            return title;
        }
    }
}
