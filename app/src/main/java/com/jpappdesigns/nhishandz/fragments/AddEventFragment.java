package com.jpappdesigns.nhishandz.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.InsertEventBackgroundWorker;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonathan.perez on 8/22/16.
 */
public class AddEventFragment extends Fragment {

    private static final String TAG = AddEventFragment.class.getSimpleName();
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private EditText mEventName;
    private EditText mEventDate;
    private Button mAddEvent;
    private String mResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_event, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        startAnimation(view);

        mEventName = (EditText) view.findViewById(R.id.etEventName);

        mEventDate = (EditText) view.findViewById(R.id.etEventDate);
        mEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateDialog();
            }
        });

        mAddEvent = (Button) view.findViewById(R.id.btnAddEvent);
        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                InsertEventBackgroundWorker insertEventBackgroundWorker = new InsertEventBackgroundWorker(
                        getActivity(), Constants.INSERT_EVENT, mEventName.getText().toString(), mEventDate.getText().toString(),
                        new AsyncStringResult() {
                            @Override
                            public void onResult(String object) {
                                mResult = object;

                                Log.d(TAG, "onResult: " + mResult);
                                if (mResult.equals("Event Added Successfully")) {
                                    FragmentManager fm = getFragmentManager();
                                    Snackbar.make(view, "Event successfully added to database", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    fm.popBackStackImmediate();


                                } else {
                                    Snackbar.make(view, "Sorry, but there seems to be a problem with your inputs.  Please verify them and try again.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
                insertEventBackgroundWorker.execute();
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

    private void startDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mEventDate.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();
    }
}
