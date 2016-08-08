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
import com.jpappdesigns.nhishandz.GetChildOfParentBackgroundWorker;
import com.jpappdesigns.nhishandz.GetSingleCustomerBackgroundWorker;
import com.jpappdesigns.nhishandz.R;

/**
 * Created by jonathan.perez on 7/25/16.
 */
public class CustomerDetailFragment extends Fragment {

    private static final String TAG = CustomerDetailFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    private String customerId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        customerId = bundle.getString("customerId");
        Log.d(TAG, "onCreateView: " + customerId);

        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        initView(view);

        getCustomerData(customerId, TAG);
        //getChildOfCustomerData(customerId);
        return view;
    }

    private void initView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCustomerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getCustomerData(String customerId, String sendingFragment) {

        GetSingleCustomerBackgroundWorker d = new GetSingleCustomerBackgroundWorker(getActivity(), Constants.RETRIEVE_SINGLE_CUSTOMER, Constants.RETRIEVE_SINGLE_CUSTOMER, mRecyclerView, customerId, sendingFragment);
        d.execute(customerId);

    }

    private void getChildOfCustomerData(String customerId) {

        GetChildOfParentBackgroundWorker getChildOfParentBackgroundWorker = new GetChildOfParentBackgroundWorker(getActivity(), Constants.RETRIVE_CHILD_OF_PARENT, mRecyclerView, customerId);
        getChildOfParentBackgroundWorker.execute(customerId);
    }
}
