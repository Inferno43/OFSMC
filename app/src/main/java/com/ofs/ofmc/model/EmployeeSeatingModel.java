package com.ofs.ofmc.model;

import android.graphics.Color;

import by.anatoldeveloper.hallscheme.hall.HallScheme;
import by.anatoldeveloper.hallscheme.hall.Seat;

/**
 * Created by saravana.subramanian on 2/28/17.
 */

public class EmployeeSeatingModel implements Seat {

    public int id;
    public int color = Color.parseColor("#37474F");
    public String marker;
    public String selectedSeatMarker;
    public HallScheme.SeatStatus status;


    @Override
    public int id() {
        return id;
    }

    @Override
    public int color() {
        return color;
    }

    @Override
    public String marker() {
        return marker;
    }

    @Override
    public String selectedSeat() {
        return selectedSeatMarker;
    }

    @Override
    public HallScheme.SeatStatus status() {
        return status;
    }

    @Override
    public void setStatus(HallScheme.SeatStatus status) {

    }
}
