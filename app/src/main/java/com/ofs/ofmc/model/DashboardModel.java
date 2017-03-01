package com.ofs.ofmc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saravana.subramanian on 2/24/17.
 */

public class DashboardModel implements Parcelable{

    private String imagePath;
    private String title;
    private String description;

    public DashboardModel(String imagePath, String title, String description) {
        this.imagePath = imagePath;
        this.title = title;
        this.description = description;
    }

    public DashboardModel() {
    }

    protected DashboardModel(Parcel in) {
        imagePath = in.readString();
        title = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(title);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DashboardModel> CREATOR = new Creator<DashboardModel>() {
        @Override
        public DashboardModel createFromParcel(Parcel in) {
            return new DashboardModel(in);
        }

        @Override
        public DashboardModel[] newArray(int size) {
            return new DashboardModel[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
