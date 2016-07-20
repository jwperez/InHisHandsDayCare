package com.jpappdesigns.nhishandz;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jpappdesigns.nhishandz.fragments.LandingPageFragment;
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
        if (pref.getBoolean(Constants.IS_LOGGED_IN, false)) {
            fragment = new LandingPageFragment();
        } else {
            fragment = new LoginFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }
}
