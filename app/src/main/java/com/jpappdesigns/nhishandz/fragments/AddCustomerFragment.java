package com.jpappdesigns.nhishandz.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.InsertCustomerBackgroundWorker;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

/**
 * Created by jonathan.perez on 8/21/16.
 */
public class AddCustomerFragment extends Fragment {

    private static final String TAG = AddCustomerFragment.class.getSimpleName();
    private EditText mLastName;
    private EditText mFirstName;
    private EditText mMiddleName;
    private EditText mEmail;
    private EditText mPhoneNumber;
    private EditText mRelationsshipToChild;
    private Button mSaveToDatabase;
    private String mResult;
    private String mSendingFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //mSendingFragment = getArguments().getString("SendingFragment");

        View view = inflater.inflate(R.layout.add_customer, container, false);

        intiView(view);

        return view;
    }

    private void intiView(View view) {

        startAnimation(view);

        mLastName = (EditText) view.findViewById(R.id.etLastName);
        mFirstName = (EditText) view.findViewById(R.id.etFirstName);
        mMiddleName = (EditText) view.findViewById(R.id.etMiddleName);
        mEmail = (EditText) view.findViewById(R.id.etEmail);
        mPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);
        mRelationsshipToChild = (EditText) view.findViewById(R.id.etRelationship);
        mSaveToDatabase = (Button) view.findViewById(R.id.btnAddCustomer);

        mSaveToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                InsertCustomerBackgroundWorker insertCustomerBackgroundWorker = new InsertCustomerBackgroundWorker(
                        getActivity(), Constants.INSERT_CUSTOMER, mLastName.getText().toString(), mFirstName.getText().toString(), mMiddleName.getText().toString(),
                        mEmail.getText().toString(), mPhoneNumber.getText().toString(), mRelationsshipToChild.getText().toString(),
                        new AsyncStringResult() {
                            @Override
                            public void onResult(String object) {
                                mResult = object;

                                Log.d(TAG, "onResult: " + mResult);
                                if (mResult.equals("Customer Added Successfully")) {
                                    FragmentManager fm = getFragmentManager();
                                    Snackbar.make(view, "Customer successfully added to database", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    fm.popBackStackImmediate();

                                } else {
                                    Snackbar.make(view, "Sorry, but there seems to be a problem with your inputs.  Please verify them and try again.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
                insertCustomerBackgroundWorker.execute();
            }
        });
    }

    private void startAnimation(View view) {

        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linLayout);
        int colorFrom = getResources().getColor(R.color.colorAccent);
        int colorTo = getResources().getColor(R.color.white);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(1900);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                linearLayout.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
