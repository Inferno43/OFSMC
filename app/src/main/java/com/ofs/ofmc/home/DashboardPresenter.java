package com.ofs.ofmc.home;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class DashboardPresenter implements HomeContract.PresenterDashboard {

    HomeContract.ViewDashboard viewDashboard;

    public DashboardPresenter(HomeContract.ViewDashboard viewDashboard) {
        this.viewDashboard = viewDashboard;
        this.viewDashboard.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
