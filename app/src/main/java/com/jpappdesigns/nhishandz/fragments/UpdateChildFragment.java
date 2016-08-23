package com.jpappdesigns.nhishandz.fragments;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.CustomerDownloader;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.UpdateChildBackgroundWorker;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonathan.perez on 8/23/16.
 */
public class UpdateChildFragment extends Fragment {

    private static final String TAG = UpdateChildFragment.class.getSimpleName();
    private String mChildId;
    private String mChildLastName;
    private String mChildFirstName;
    private String mChildMiddleName;
    private String mChildDob;
    private Spinner mCustomerSpinner;
    private String mCustomerId;
    private String mResult;
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mChildId = getArguments().getString("ChildId");
        mChildLastName = getArguments().getString("ChildLastName");
        mChildFirstName = getArguments().getString("ChildFirstName");
        mChildMiddleName = getArguments().getString("ChildMiddleName");
        mChildDob = getArguments().getString("ChildDob");

        View view = inflater.inflate(R.layout.update_child, container, false);

        initView(view);

        populateSpinner();

        return view;
    }

    private void initView(View view) {

        final EditText childLastName = (EditText) view.findViewById(R.id.etLastName);
        final EditText childFirstName = (EditText) view.findViewById(R.id.etFirstName);
        final EditText childMiddleName = (EditText) view.findViewById(R.id.etMiddleName);
        final EditText childDob = (EditText) view.findViewById(R.id.etDOB);
        mCustomerSpinner = (Spinner) view.findViewById(R.id.spCustomer);
        Button mUpdateChild = (Button) view.findViewById(R.id.btnUpdateChild);

        childLastName.setText(mChildLastName);
        childFirstName.setText(mChildFirstName);
        childMiddleName.setText(mChildMiddleName);
        childDob.setText(mChildDob);

        childDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateDialog();
            }

            private void startDateDialog() {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        childDob.setText(dateFormatter.format(newDate.getTime()));

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                fromDatePickerDialog.show();
            }
        });

        mUpdateChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                UpdateChildBackgroundWorker updateChildBackgroundWorker = new UpdateChildBackgroundWorker(getActivity(), mChildId, childLastName.getText().toString(), childFirstName.getText().toString(), childMiddleName.getText().toString(),
                        childDob.getText().toString(), mCustomerId,
                         new AsyncStringResult() {

                    @Override
                    public void onResult(String object) {
                        mResult = object;

                        Log.d(TAG, "onResult: " + mResult);
                        if (mResult.equals("Customer Updated Successfully")) {
                            FragmentManager fm = getFragmentManager();
                            Snackbar.make(view, "Customer successfully updated in database", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            fm.popBackStackImmediate();

                        } else {
                            Snackbar.make(view, "Sorry, but there seems to be a problem with your inputs.  Please verify them and try again.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
                updateChildBackgroundWorker.execute();
            }
        });

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
    }
    private void populateSpinner() {

        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CUSTOMERS_URL, mCustomerSpinner);
        d.execute();
    }


}
