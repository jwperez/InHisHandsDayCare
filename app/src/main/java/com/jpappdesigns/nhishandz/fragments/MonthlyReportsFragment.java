package com.jpappdesigns.nhishandz.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.jpappdesigns.nhishandz.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private String mChildId;
    private String mCustomerId;
    private LinearLayout mReportsStarter;

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
        mReportsStarter = (LinearLayout) view.findViewById(R.id.llReportStarter);

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

        populateSpinner();

        mChildrenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ViewGroup vg = (ViewGroup) view;
                vg.getChildCount();

                TextView childId = (TextView) vg.getChildAt(1);
                TextView customerId = (TextView) vg.getChildAt(2);

                mChildId = childId.getText().toString();
                mCustomerId = customerId.getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mGenerateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mReportsStarter.setBackgroundColor(Color.parseColor("#6dafdc"));
                mGenerateBill.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mGenerateBill.setText("Bill Generated.  See Below.");
                mGenerateBill.setTextColor(Color.parseColor("#FFC107"));

                Fragment fragment;
                fragment = new MonthlyReportsPrintoutFragment();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putString("ChildId", mChildId);
                args.putString("CustomerId", mCustomerId);
                args.putString("StartDate", String.valueOf(mStartDate.getText()));
                args.putString("EndDate", String.valueOf(mEndDate.getText()));
                fragment.setArguments(args);
                ft.replace(R.id.fragment_report_printout, fragment);
                ft.commit();
            }
        });
    }

    private void populateSpinner() {

        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CHILDREN_URL, mChildrenSpinner, TAG);
        d.execute();
    }
}
