package com.jpappdesigns.nhishandz.model;

/**
 * Created by jonathan.perez on 8/1/16.
 */
public class ChildSessionModel {

    private String date;
    private String mTimeIn;
    private String mTimeOut;
    private String mDuration;
    private String mChildId;
    private String mSessionCost;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeIn() {
        return mTimeIn;
    }

    public void setTimeIn(String timeIn) {
        mTimeIn = timeIn;
    }

    public String getTimeOut() {
        return mTimeOut;
    }

    public void setTimeOut(String timeOut) {
        mTimeOut = timeOut;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getSessionCost() {
        return mSessionCost;
    }

    public String getChildId() {
        return mChildId;
    }

    public void setChildId(String childId) {
        mChildId = childId;
    }

    public void setSessionCost(String sessionCost) {
        mSessionCost = sessionCost;
    }
}
