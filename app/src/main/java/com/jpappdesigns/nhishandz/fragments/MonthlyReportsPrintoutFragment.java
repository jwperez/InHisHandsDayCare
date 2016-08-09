package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpappdesigns.nhishandz.R;

/**
 * Created by jonathan.perez on 8/8/16.
 */
public class MonthlyReportsPrintoutFragment extends Fragment {

    private static final String TAG = MonthlyReportsPrintoutFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_printout, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerView);
        retrieveData();

    }

    private void retrieveData() {

        //GetSingleCustomerBackgroundWorker backgroundWorker = new GetSingleCustomerBackgroundWorker(getActivity(), Constants.RETRIEVE_SINGLE_CUSTOMER,
                //Constants.RETRIEVE_CHILD_BY_ID, mRecyclerView, customerId, childId, sendingFragment, currentDate);
    }
}
