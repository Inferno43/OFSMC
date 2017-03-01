package com.ofs.ofmc.home;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class HolidaysPresenter implements HomeContract.PresenterHolidays{

    HomeContract.ViewHolidays viewHolidays;

    public HolidaysPresenter(HomeContract.ViewHolidays viewHolidays) {
        this.viewHolidays = viewHolidays;
        this.viewHolidays.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
