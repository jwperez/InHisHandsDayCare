package com.jpappdesigns.nhishandz.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.jpappdesigns.nhishandz.GetSingleSessionBackgroundWorker;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.UpdateChildSessionBackgroundWorker;
import com.jpappdesigns.nhishandz.model.ChildSessionModel;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jonathan.perez on 8/14/16.
 */
public class UpdateSessionFragment extends Fragment {

    private static final String TAG = UpdateSessionFragment.class.getSimpleName();
    private Spinner mChildrenSpinner;
    private TextView mDate;
    private Button mSearchDatabase;
    private Button mUpdateDatabase;
    private String mChildId;
    private String mResult;
    private ArrayList<ChildSessionModel> mChildSessionModels = new ArrayList<>();
    private TextView mTimeIn;
    private TextView mTimeOut;
    int hour, minute, second;
    private DecimalFormat formatter = new DecimalFormat("##");
    private String mDisplayTime;
    private String mOutTime;
    private String mIntime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_session_search, container, false);

        initView(view);

        populateSpinner();

        return view;
    }

    private void initView(View view) {

        mChildrenSpinner = (Spinner) view.findViewById(R.id.spChildren);
        mDate = (TextView) view.findViewById(R.id.tvDate);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        mSearchDatabase = (Button) view.findViewById(R.id.btnSearchDatabase);
        mTimeIn = (TextView) view.findViewById(R.id.tvTimeIn);
        mTimeOut = (TextView) view.findViewById(R.id.tvTimeOut);
        mUpdateDatabase = (Button) view.findViewById(R.id.btnUpdateDatabase);

        mDate.setOnClickListener(new View.OnClickListener() {
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
                        mDate.setText(dateFormatter.format(newDate.getTime()));

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                fromDatePickerDialog.show();
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

        mSearchDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetSingleSessionBackgroundWorker singleSessionBackgroundWorker = new
                        GetSingleSessionBackgroundWorker(getActivity(), Constants.RETRIEVE_SINGLE_SESSION, mChildId, mDate.getText().toString(),
                        new AsyncStringResult() {
                    @Override
                    public void onResult(String object) {
                        mResult = object;
                        parseChildSessions();
                    }
                });
                singleSessionBackgroundWorker.execute();
            }
        });
    }

    private void populateSpinner() {

        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CHILDREN_URL, mChildrenSpinner, TAG);
        d.execute();
    }

    private int parseChildSessions() {

        try {
            JSONArray childSession;
            JSONObject jsonObject = new JSONObject(mResult);
            childSession = jsonObject.getJSONArray("child_sessions");

            mChildSessionModels.clear();

            for (int i = 0; i < childSession.length(); i++) {
                JSONObject c = childSession.getJSONObject(i);
                ChildSessionModel childSessionModel = new ChildSessionModel();
                childSessionModel.setDate(c.getString("date"));
                childSessionModel.setTimeIn(c.getString("timeIn"));
                childSessionModel.setTimeOut(c.getString("timeOut"));
                childSessionModel.setDuration(c.getString("duration"));
                childSessionModel.setChildId(c.getString("childId"));
                childSessionModel.setSessionCost(c.getString("sessionCost"));
                mChildSessionModels.add(childSessionModel);
            }

            updateView();

            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void updateView() {

        mTimeIn.setText(mChildSessionModels.get(0).getTimeIn());
        mIntime = mChildSessionModels.get(0).getTimeIn();
        mTimeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeDialog();
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

                /*String timeSet = "";
                if (hours > 12) {
                    hours -= 12;
                    timeSet = "PM";
                } else if (hours == 0) {
                    hours += 12;
                    timeSet = "AM";
                } else if (hours == 12)
                    timeSet = "PM";
                else
                    timeSet = "AM";*/

                // Append in a StringBuilder
                mIntime = new StringBuilder().append(formatter.format(hours)).append(':')
                        .append(formatter.format(mins)).append(':').append(formatter.format(second)).toString();
                Log.d(TAG, "updateTime: " + mIntime);

                mTimeIn.setText(mIntime);
            }
        });


        mTimeOut.setText(mChildSessionModels.get(0).getTimeOut());
        mOutTime = mChildSessionModels.get(0).getTimeOut();
        mTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeDialog();
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

                /*String timeSet = "";
                if (hours > 12) {
                    hours -= 12;
                    timeSet = "PM";
                } else if (hours == 0) {
                    hours += 12;
                    timeSet = "AM";
                } else if (hours == 12)
                    timeSet = "PM";
                else
                    timeSet = "AM";*/

                // Append in a StringBuilder
                mOutTime = new StringBuilder().append(formatter.format(hours)).append(':')
                        .append(formatter.format(mins)).append(':').append(formatter.format(second)).toString();
                Log.d(TAG, "updateTime: " + mOutTime);

                mTimeOut.setText(mOutTime);
            }
        });

        mUpdateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateChildSessionBackgroundWorker updateChildSessionBackgroundWorker =
                        new UpdateChildSessionBackgroundWorker(getActivity(), Constants.UPDATE_SESSION,
                                mDate.getText().toString(), mIntime, mOutTime, mChildId);
                updateChildSessionBackgroundWorker.execute();
            }
        });
    }


}
