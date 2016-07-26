package com.jpappdesigns.nhishandz.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.adapter.HomescreenAdapter;
import com.jpappdesigns.nhishandz.model.HomescreenData;

public class HomescreenFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomescreenAdapter mHomescreenAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        initViews(view);
        return view;

    }

    private void initViews(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mHomescreenAdapter = new HomescreenAdapter(getActivity(), HomescreenData.getData());

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHomescreenAdapter);
    }
}
