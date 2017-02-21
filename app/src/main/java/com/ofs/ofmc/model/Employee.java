package com.ofs.ofmc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class Employee implements Parcelable  {

    private String userId;
    private String employeeId;
    private String employeeName;
    private String employeeDepartment;
    private String employeePassword;
    private String employeeEmail;
    private String employeePhase;
    private String employeeExtension;

    public Employee(String userId, String employeeId, String employeeName, String employeeDepartment,
                    String employeePassword, String employeeEmail, String employeePhase, String employeeExtension) {
        this.userId = userId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
        this.employeePassword = employeePassword;
        this.employeeEmail = employeeEmail;
        this.employeePhase = employeePhase;
        this.employeeExtension = employeeExtension;
    }


    protected Employee(Parcel in) {
        userId = in.readString();
        employeeId = in.readString();
        employeeName = in.readString();
        employeeDepartment = in.readString();
        employeePassword = in.readString();
        employeeEmail = in.readString();
        employeePhase = in.readString();
        employeeExtension = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(employeeId);
        dest.writeString(employeeName);
        dest.writeString(employeeDepartment);
        dest.writeString(employeePassword);
        dest.writeString(employeeEmail);
        dest.writeString(employeePhase);
        dest.writeString(employeeExtension);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePhase() {
        return employeePhase;
    }

    public void setEmployeePhase(String employeePhase) {
        this.employeePhase = employeePhase;
    }

    public String getEmployeeExtension() {
        return employeeExtension;
    }

    public void setEmployeeExtension(String employeeExtension) {
        this.employeeExtension = employeeExtension;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
