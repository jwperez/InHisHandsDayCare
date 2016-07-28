package com.jpappdesigns.nhishandz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jpappdesigns.nhishandz.fragments.HomescreenFragment;
import com.jpappdesigns.nhishandz.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getPreferences(0);
        initFragement();
    }

    private void initFragement() {
        Fragment fragment;
        if (pref.getBoolean(Constants.IS_LOGGED_IN, true)) {
            fragment = new HomescreenFragment();
            //fragment = new CustomerListFragment();
        } else {
            fragment = new LoginFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
