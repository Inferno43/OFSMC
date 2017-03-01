package com.ofs.ofmc.home;

/**
 * Created by saravana.subramanian on 2/28/17.
 */

public class EmployeeSeatingPresenter implements HomeContract.PresenterEmployeeSeating {

    HomeContract.ViewEmployeeSeating viewEmployeeSeating;
    public EmployeeSeatingPresenter(HomeContract.ViewEmployeeSeating viewEmployeeSeating) {
        this.viewEmployeeSeating = viewEmployeeSeating;
        this.viewEmployeeSeating.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
