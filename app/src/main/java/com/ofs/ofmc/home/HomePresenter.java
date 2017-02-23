package com.ofs.ofmc.home;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class HomePresenter implements HomeContract.PresenterHome{

    HomeContract.ViewHome viewHome;

    public HomePresenter(HomeContract.ViewHome viewHome) {
        this.viewHome = viewHome;
        viewHome.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
