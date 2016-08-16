package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jonathan.perez on 8/13/16.
 */
public class TestFragment extends Fragment {

    private static final String TAG = TestFragment.class.getSimpleName();
    private Button mRecordSession;
    private Button mUpdateSession;
    private String mUrlAddress = Constants.INSERT_CHILD_SESSION;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_session_selection, container, false);

        initView(view);
        calculateDates();

        return view;
    }

    private void initView(View view) {

        mRecordSession = (Button) view.findViewById(R.id.btnRecordSession);
        mUpdateSession = (Button) view.findViewById(R.id.btnUpdateSession);

        mRecordSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment;
                fragment = new RecordSessionFragment();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flSession, fragment);
                ft.commit();
            }
        });

        mUpdateSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment;
                fragment = new UpdateSessionFragment();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flSession, fragment);
                ft.commit();
            }
        });
    }

    private void calculateDates() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.WEEK_OF_YEAR, 0);
        Log.d(TAG, "calculateDates: " + (sdf.format(cal.getTime())));
        cal.add(Calendar.WEEK_OF_YEAR, 0);
        Log.d(TAG, "calculateDates: " + (sdf.format(cal.getTime())));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //cal.add(Calendar.DAY_OF_WEEK,6);
        Log.d(TAG, "calculateDates: " + (sdf.format(cal.getTime())));
    }
}
