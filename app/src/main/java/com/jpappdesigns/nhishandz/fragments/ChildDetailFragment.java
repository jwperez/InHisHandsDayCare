package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.GetSingleCustomerBackgroundWorker;
import com.jpappdesigns.nhishandz.R;

import java.util.Calendar;

/**
 * Created by jonathan.perez on 7/27/16.
 */
public class ChildDetailFragment extends Fragment {

    private static final String TAG = ChildDetailFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private String mCustomerId;
    private String mChildId;
    private Calendar mCurrentDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        mCustomerId = bundle.getString("customerId");
        mChildId = bundle.getString("childId");

        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        initView(view);

        mCurrentDate = getCurrentDateTime();
        //Log.d(TAG, "onCreateView: " + mCurrentDate);

        getData(mCustomerId, mChildId, TAG, mCurrentDate);

        return view;
    }

    private void initView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCustomerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private Calendar getCurrentDateTime() {

        Calendar calander = Calendar.getInstance();
        //SimpleDateFormat sdf = calander.DATE;
        calander.add(Calendar.DAY_OF_YEAR, -7);

        return calander;

    }

    private void getData(String customerId, String childId, String sendingFragment, Calendar currentDate) {

        GetSingleCustomerBackgroundWorker d = new GetSingleCustomerBackgroundWorker(getActivity(), Constants.RETRIEVE_SINGLE_CUSTOMER, Constants.RETRIEVE_CHILD_BY_ID, mRecyclerView, customerId, childId, sendingFragment, currentDate);
        d.execute(customerId);
    }

}
