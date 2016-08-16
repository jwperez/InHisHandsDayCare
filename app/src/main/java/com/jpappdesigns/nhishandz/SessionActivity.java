package com.jpappdesigns.nhishandz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jpappdesigns.nhishandz.fragments.TestFragment;

/**
 * Created by jonathan.perez on 8/14/16.
 */
public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        intiFragment();
    }

    private void intiFragment() {

        Fragment fragment;
        fragment = new TestFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flSessionSelection, fragment);
        ft.commit();

    }
}
