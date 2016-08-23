package com.jpappdesigns.nhishandz.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.CustomerDownloader;
import com.jpappdesigns.nhishandz.InsertChildBackgroundWorker;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonathan.perez on 8/20/16.
 */
public class AddChildFragment extends Fragment {

    private static final String TAG = AddChildFragment.class.getSimpleName();
    private EditText mDOB;
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Spinner mCustomerSpinner;
    private String mCustomerId;
    private Button mAddCustomer;
    private Button mAddChild;
    private EditText mLastName;
    private EditText mFirstName;
    private EditText mMiddleName;
    private String mResult;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_child,container, false);

        intiView(view);

        return view;
    }

    private void intiView(View view) {

        startAnimation(view);

        mCustomerSpinner = (Spinner) view.findViewById(R.id.spCustomer);

        mDOB = (EditText) view.findViewById(R.id.etDOB);
        mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateDialog();
            }
        });

        populateSpinner();

        mCustomerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ViewGroup vg = (ViewGroup) view;
                vg.getChildCount();

                TextView customerId = (TextView) vg.getChildAt(1);
                Log.d(TAG, "onItemSelected: " + customerId);

                mCustomerId = customerId.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mLastName = (EditText) view.findViewById(R.id.etLastName);
        mFirstName = (EditText) view.findViewById(R.id.etFirstName);
        mMiddleName = (EditText) view.findViewById(R.id.etMiddleName);
        mDOB = (EditText) view.findViewById(R.id.etDOB);

        mAddCustomer = (Button) view.findViewById(R.id.btnAddCustomer);
        mAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTransition(view);
            }
        });

        mAddChild = (Button) view.findViewById(R.id.btnAddChild);
        mAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                InsertChildBackgroundWorker insertChildBackgroundWorker = new InsertChildBackgroundWorker(
                        getActivity(), Constants.INSERT_CHILD, mLastName.getText().toString(), mFirstName.getText().toString(), mMiddleName.getText().toString(),
                        mDOB.getText().toString(), mCustomerId,
                        new AsyncStringResult() {
                            @Override
                            public void onResult(String object) {
                                mResult = object;

                                Log.d(TAG, "onResult: " + mResult);
                                if (mResult.equals("Child Added Successfully")) {
                                    FragmentManager fm = getFragmentManager();
                                    Snackbar.make(view, "Child successfully added to database", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    fm.popBackStackImmediate();


                                } else {
                                    Snackbar.make(view, "Sorry, but there seems to be a problem with your inputs.  Please verify them and try again.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
                insertChildBackgroundWorker.execute();
            }
        });
    }

    private void setTransition(View view) {

        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion));
        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));


        Fragment addCustomer = new AddCustomerFragment();
        addCustomer.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion));
        addCustomer.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));

        View viewStart = view.findViewById(R.id.btnAddCustomer);

        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_frame, addCustomer)
                .addToBackStack("transition")
                .addSharedElement(viewStart, "hello");
        ft.commit();
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
                mDOB.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();
    }

    private void populateSpinner() {

        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CUSTOMERS_URL, mCustomerSpinner);
        d.execute();
    }
}
