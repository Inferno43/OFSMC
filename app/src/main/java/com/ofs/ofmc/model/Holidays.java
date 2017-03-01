package com.ofs.ofmc.model;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class Holidays {

    private String holidayDate;
    private String holidayDay;
    private String holidayDescription;
    private boolean isTentative;

    public Holidays(String holidayDate,String holidayDay, String holidayDescription,boolean isTentative) {
        this.holidayDate = holidayDate;
        this.holidayDay = holidayDay;
        this.holidayDescription = holidayDescription;
        this.isTentative = isTentative;
    }

    public String getHolidayDay() {
        return holidayDay;
    }

    public void setHolidayDay(String holidayDay) {
        this.holidayDay = holidayDay;
    }

    public String getHolidayDescription() {
        return holidayDescription;
    }

    public void setHolidayDescription(String holidayDescription) {
        this.holidayDescription = holidayDescription;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public boolean isTentative() {
        return isTentative;
    }

    public void setTentative(boolean tentative) {
        isTentative = tentative;
    }
}
