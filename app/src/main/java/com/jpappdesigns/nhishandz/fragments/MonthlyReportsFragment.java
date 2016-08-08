package com.jpappdesigns.nhishandz.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.CustomerDownloader;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.ChildModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jonathan.perez on 8/6/16.
 */
public class MonthlyReportsFragment extends Fragment {

    private static final String TAG = MonthlyReportsFragment.class.getSimpleName();
    private EditText mStartDate;
    private EditText mEndDate;
    private Button mGenerateBill;
    private SimpleDateFormat mDateFormatter;

    private Spinner mChildrenSpinner;

    private ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_starter, container, false);

        mDateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        initView(view);

        return view;
    }

    private void initView(View view) {

        mChildrenSpinner = (Spinner) view.findViewById(R.id.sChildren);
        mStartDate = (EditText) view.findViewById(R.id.etStartDate);
        mEndDate = (EditText) view.findViewById(R.id.etEndDate);
        mGenerateBill = (Button) view.findViewById(R.id.btGenerateBill);

        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateDialog();
            }

            private void startDateDialog() {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(),  new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        mStartDate.setText(mDateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                fromDatePickerDialog.show();
            }
        });

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateDialog();
            }

            private void endDateDialog() {

                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        mEndDate.setText(mDateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                toDatePickerDialog.show();
            }
        });

        populateSpinner(mChildrenSpinner);

        mGenerateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment;
                fragment = new MonthlyReportsFragment();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_report_printout, fragment);
                ft.commit();
            }
        });
    }

    private void populateSpinner(Spinner spinner) {

        List<ChildModel> children = new ArrayList<>();

        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CHILDREN_URL, mChildrenSpinner, TAG);
        d.execute();
    }
}
