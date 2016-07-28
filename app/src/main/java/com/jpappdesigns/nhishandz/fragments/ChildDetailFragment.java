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
 * Created by jonathan.perez on 7/27/16.
 */
public class ChildDetailFragment extends Fragment {

    private static final String TAG = ChildDetailFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    private String mCustomerId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        mCustomerId = bundle.getString("customerId");
        Log.d(TAG, "onCreateView: " + mCustomerId);

        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        initView(view);

        getData(mCustomerId, TAG);

        return view;
    }

    private void initView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCustomerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getData(String customerId, String sendingFragment) {

        GetSingleCustomerBackgroundWorker d = new GetSingleCustomerBackgroundWorker(getActivity(), Constants.RETRIEVE_SINGLE_CUSTOMER, Constants.RETRIEVE_SINGLE_CUSTOMER, mRecyclerView, customerId, sendingFragment);
        d.execute(customerId);
    }

}
