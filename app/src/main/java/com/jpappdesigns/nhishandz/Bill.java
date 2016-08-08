package com.jpappdesigns.nhishandz;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by jonathan.perez on 8/4/16.
 */
public class Bill {

    private static final String TAG = Bill.class.getSimpleName();
    private Context mContext;
    private String childId;
    private String mUrlAddress = Constants.RETRIEVE_CHILD_SESSION_FOR_WEEK;
    private Calendar mBeginningDate;
    private Calendar mEndDate;

    public Bill(Context context, String urlAddress, String childId, Calendar beginningDate, Calendar endDate) {
        mContext = context;
        mUrlAddress = urlAddress;
        this.childId = childId;
        mBeginningDate = beginningDate;
        mEndDate = endDate;

        //getData(mContext, mUrlAddress, childId, mBeginningDate, mEndDate);
    }

    private void getData(Context context, String urlAddress, String childId, Calendar beginningDate, Calendar endDate) {

        //BillsBackgroundWorker d = new BillsBackgroundWorker(context, mUrlAddress, childId, beginningDate, endDate);
        //d.execute();
    }
}
