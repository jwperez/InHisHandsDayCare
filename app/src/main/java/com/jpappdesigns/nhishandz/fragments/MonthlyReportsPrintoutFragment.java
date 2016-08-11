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
import com.jpappdesigns.nhishandz.GetSingleCustomerBackgroundWorker;
import com.jpappdesigns.nhishandz.R;

/**
 * Created by jonathan.perez on 8/8/16.
 */
public class MonthlyReportsPrintoutFragment extends Fragment {

    private static final String TAG = MonthlyReportsPrintoutFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private String mChildId;
    private String mCustomerId;
    private String mStartDate;
    private String mEndDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChildId = getArguments().getString("ChildId");
        mCustomerId = getArguments().getString("CustomerId");
        mStartDate = getArguments().getString("StartDate");
        mEndDate = getArguments().getString("EndDate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_printout, container, false);
        initView(view);
        retrieveData();

        return view;
    }

    private void initView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void retrieveData() {

        GetSingleCustomerBackgroundWorker backgroundWorker = new GetSingleCustomerBackgroundWorker(getActivity(), Constants.RETRIEVE_SINGLE_CUSTOMER,
                Constants.RETRIEVE_CHILD_BY_ID, mRecyclerView, mCustomerId, mChildId, TAG, mStartDate, mEndDate);
        Log.d(TAG, "retrieveData: CustomerId" + mCustomerId);
        Log.d(TAG, "retrieveData: " + mChildId);
        backgroundWorker.execute(mCustomerId, mChildId);
    }
}
