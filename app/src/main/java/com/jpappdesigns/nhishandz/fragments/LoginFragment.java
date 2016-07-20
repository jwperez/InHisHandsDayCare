package com.jpappdesigns.nhishandz.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.BackgroundWorker;
import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView tv_register;
    private ProgressBar progress;
    private SharedPreferences pref;
    private String result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        pref = getActivity().getPreferences(0);

        btn_login = (AppCompatButton)view.findViewById(R.id.btn_login);
        /*btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_email.getText().toString();
                String password = et_password.getText().toString();
                String type = Constants.LOGIN_OPERATION;

                BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
                backgroundWorker.execute(type, username, password);
            }
        });*/
        //tv_register = (TextView)view.findViewById(R.id.tv_register);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String username = et_email.getText().toString();
        String password = et_password.getText().toString();
        String type = Constants.LOGIN_OPERATION;

        /*BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
        backgroundWorker.execute(type, username, password);*/

        if(!username.isEmpty() && !password.isEmpty()) {

            progress.setVisibility(View.VISIBLE);
            loginProcess(type, username,password);


        } else {

            Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
        }
    }

    private void loginProcess(String type, String username, String password) {

        BackgroundWorker backgroundworker = new BackgroundWorker(getActivity(), new AsyncStringResult() {
            @Override
            public void onResult(String result) {
                Log.d(TAG, "onResult: " + result);
                if(result.equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    goToProfile();
                }
            }
        });
        backgroundworker.execute(type, username, password);
    }

    private void goToProfile(){

       Fragment landingPage = new LandingPageFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, landingPage);
        ft.commit();
    }
}
