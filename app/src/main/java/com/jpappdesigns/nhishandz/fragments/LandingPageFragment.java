package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.adapter.LandingPageAdapter;

public class LandingPageFragment extends Fragment {

    private GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        initViews(view);
        return view;

    }

    private void initViews(View view) {

        mGridView = (GridView) view.findViewById(R.id.gridView);
        mGridView.setAdapter(new LandingPageAdapter(getActivity()));
    }
}
