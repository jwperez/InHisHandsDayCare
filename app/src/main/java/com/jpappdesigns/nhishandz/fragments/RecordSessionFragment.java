package com.jpappdesigns.nhishandz.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.CustomerDownloader;
import com.jpappdesigns.nhishandz.InsertChildSessionBackgroundWorker;
import com.jpappdesigns.nhishandz.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonathan.perez on 8/14/16.
 */
public class RecordSessionFragment extends Fragment {

    private static final String TAG = RecordSessionFragment.class.getSimpleName();
    static final int TIME_DIALOG_ID = 1111;
    private Spinner mChildrenSpinner;
    private TextView mDate;
    private TextView mTimeIn;
    private TextView mTimeOut;
    int hour,minute,second;
    private String mChildId;
    private Button mSaveToDatabase;
    final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss", Locale.US);
    private String mDisplayTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_record_session, container, false);

        initView(view);

        populateSpinner();

        return view;
    }

    private void initView(View view) {

        mChildrenSpinner = (Spinner) view.findViewById(R.id.spChildren);
        mDate = (TextView) view.findViewById(R.id.etDate);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        mTimeIn = (TextView) view.findViewById(R.id.tvTimeIn);
        mTimeOut = (TextView) view.findViewById(R.id.tvTimeOut);
        mSaveToDatabase = (Button) view.findViewById(R.id.btnSaveToDatabase);

        //updateTime(hour,minute);

        mDate.setOnClickListener(new View.OnClickListener() {
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
                        mDate.setText(dateFormatter.format(newDate.getTime()));

                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                fromDatePickerDialog.show();
            }
        });

        mTimeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createDialog(TIME_DIALOG_ID).show();
                startTimeDialog();
            }

        });

        mChildrenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ViewGroup vg = (ViewGroup) view;
                vg.getChildCount();

                TextView childId = (TextView) vg.getChildAt(1);

                mChildId = childId.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSaveToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InsertChildSessionBackgroundWorker insertChildSessionBackgroundWorker = new InsertChildSessionBackgroundWorker(getActivity(),
                        Constants.INSERT_CHILD_SESSION, mDate.getText().toString(), mTimeIn.getText().toString(), mTimeOut.getText().toString(), mChildId);
                insertChildSessionBackgroundWorker.execute();
            }
        });
    }

    private void startTimeDialog() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        second = 00;

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        mDisplayTime = hourOfDay + ":" + minute;



                        //mTimeIn.setText(hourOfDay + ":" + minute);
                        updateTime(hourOfDay, minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(mins).append(" ").append(timeSet).toString();

        mTimeIn.setText(aTime);
    }

    private void populateSpinner() {

        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CHILDREN_URL, mChildrenSpinner, TAG);
        d.execute();
    }
}
