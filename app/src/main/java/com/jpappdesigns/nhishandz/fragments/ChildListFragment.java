package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.CustomerDownloader;
import com.jpappdesigns.nhishandz.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jonathan.perez on 7/23/16.
 */
public class ChildListFragment extends Fragment{

    private static final String TAG = ChildListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCustomerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getData();
        //getStartEndOFWeek(1, 2016);
        getCurrentDateTime();

        return view;
    }

    private void getData() {
        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CHILDREN_URL, mRecyclerView, TAG);
        d.execute();
    }

    void getStartEndOFWeek(int enterWeek, int enterYear){
//enterWeek is week number
//enterYear is year
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
        calendar.set(Calendar.YEAR, enterYear);

        SimpleDateFormat formatter = new SimpleDateFormat("ddMMM yyyy"); // PST`
        Date startDate = calendar.getTime();
        String startDateInStr = formatter.format(startDate);
        Log.d(TAG, "getStartEndOFWeek: " + startDateInStr);
        //System.out.println("...date..."+startDateInStr);

        calendar.add(Calendar.DATE, 6);
        Date enddate = calendar.getTime();
        String endDaString = formatter.format(enddate);
        Log.d(TAG, "getStartEndOFWeek: " + endDaString);
        System.out.println("...date..."+endDaString);
    }

    private void getCurrentDateTime() {

        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);
        String selectedMonth = "" + cMonth;
        String selectedYear = "" + cYear;
        int cHour = calander.get(Calendar.HOUR);
        int cMinute = calander.get(Calendar.MINUTE);
        int cSecond = calander.get(Calendar.SECOND);
        int cWeek = calander.get(Calendar.WEEK_OF_YEAR);


        Log.d(TAG, "getCurrentDateTime: " + cDay + "\n" + cMonth + "\n" +
        cYear + "\n" + cHour + "\n" +
        cMinute + "\n" + cSecond + "\n" + cWeek);

        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta );
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "getCurrentDateTime: " + days[0].toString());
        Log.d(TAG, "getCurrentDateTime: " + days[1].toString());
        Log.d(TAG, "getCurrentDateTime: " + days[2].toString());
        Log.d(TAG, "getCurrentDateTime: " + days[3].toString());
        Log.d(TAG, "getCurrentDateTime: " + days[4].toString());
        Log.d(TAG, "getCurrentDateTime: " + days[5].toString());
        Log.d(TAG, "getCurrentDateTime: " + days[6].toString());

    }
}
