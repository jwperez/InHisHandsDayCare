package com.jpappdesigns.nhishandz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jpappdesigns.nhishandz.fragments.MonthlyReportsFragment;

/**
 * Created by jonathan.perez on 8/6/16.
 */
public class MonthlyReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_reports);
        intiFragment();
    }

    private void intiFragment() {

        Fragment fragment;
        fragment = new MonthlyReportsFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_report_options, fragment);
        ft.commit();

    }
}
