package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.ChildModel;
import com.jpappdesigns.nhishandz.model.ChildSessionModel;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jonathan.perez on 7/26/16.
 */
public class ChildDetailAdapter extends RecyclerView.Adapter<ChildDetailAdapter.ViewHolder> {

    private static final String TAG = ChildDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<CustomerModel> mCustomer;
    private List<ChildModel> mChild;
    private List<ChildSessionModel> mChildSessions;

    public ChildDetailAdapter(Context context, List<CustomerModel> customer, List<ChildModel> child, List<ChildSessionModel> childSessions) {
        mContext = context;
        mCustomer = customer;
        mChild = child;
        mChildSessions = childSessions;
        Log.d(TAG, "ChildDetailAdapter: " + mChildSessions.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case Constants.CHILD_INFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.child_info_card, parent, false);
                return new ChildInfoHolder(view);
            case Constants.CHILD_SESSIONS:
                view = LayoutInflater.from(mContext).inflate(R.layout.child_sessions_card, parent, false);
                return new ChildSessionHolder(view);
            case Constants.PARENT_GUARDIAN_OF_CHILD:
                view = LayoutInflater.from(mContext).inflate(R.layout.customer_detail_card, parent, false);
                return new CustomerInfoHolder(view);
            default:
                throw new RuntimeException("viewType ERROR");
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position < mChild.size()) {
            return Constants.CHILD_INFO;
        } else if (position < mChild.size() + 1) {
            return Constants.PARENT_GUARDIAN_OF_CHILD;
        } else {
            return Constants.CHILD_SESSIONS;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == Constants.CHILD_INFO) {
            ChildInfoHolder childInfoHolder = (ChildInfoHolder) holder;
            StringBuilder buf = new StringBuilder();
            buf.append(mChild.get(position).getLastName());
            buf.append(", " + mChild.get(position).getFirstName());
            if (!"".equals(mChild.get(position).getMiddleName())) {
                buf.append(" ");
                buf.append(mChild.get(position).getMiddleName());
            } else {
            }
            childInfoHolder.mChildName.setText(buf.toString());
            childInfoHolder.mChildDob.setText(mChild.get(position).getDob());
        } else if (holder.getItemViewType() == Constants.PARENT_GUARDIAN_OF_CHILD) {
            CustomerInfoHolder customerInfoHolder = (CustomerInfoHolder) holder;
            customerInfoHolder.mRelationship.setText(mCustomer.get(position - 1).getRelationshipToChild());

            StringBuilder buf = new StringBuilder();
            buf.append(mCustomer.get(position - 1).getLastName());
            buf.append(", " + mCustomer.get(position - 1).getFirstName());
            if (!"".equals(mCustomer.get(position - 1).getMiddleName())) {
                buf.append(" ");
                buf.append(mCustomer.get(position - 1).getMiddleName());
            } else {
            }
            customerInfoHolder.mName.setText(buf.toString());
            customerInfoHolder.mPhoneNumber.setText(mCustomer.get(position - 1).getPhoneNumber());
            customerInfoHolder.mEmail.setText(mCustomer.get(position - 1).getEmail());

        } else {
            ChildSessionHolder childSessionHolder = (ChildSessionHolder) holder;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat timeFormat2 = new SimpleDateFormat("hh:mm aa");

            SimpleDateFormat timeOutFormat = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat timeOutFormat2 = new SimpleDateFormat("hh:mm aa");

            SimpleDateFormat duration = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat durationParsed = new SimpleDateFormat("hh:mm");

            SimpleDateFormat durationHour = new SimpleDateFormat("hh");
            SimpleDateFormat durationMinutes = new SimpleDateFormat("mm");

            Log.d(TAG, "onBindViewHolder: " + mChildSessions);

            for (ChildSessionModel dates : mChildSessions) {

                String eachDate;
                Date date;
                String parsedDate = null;
                String dayOfWeek = null;
                Date timeInParsing;
                String parsedTimeIn = null;
                Date timeOutParsing;
                String parsedTimeOut = null;
                eachDate = dates.getDate();
                Date durationParsing;
                String parsedDuration = null;
                Date durationInHour;
                int durationsHour = 0;
                double durationsMinutes = 0;

                String timeIn = dates.getTimeIn();
                String timeOut = dates.getTimeOut();
                String daysDuration = dates.getDuration();

                double sessionCost;
                double formattedSessionCost = 0;

                try {
                    date = dateFormat.parse(eachDate);

                    parsedDate = dateFormat2.format(date);

                    dayOfWeek = simpleDateFormat.format(date);

                    timeInParsing = timeFormat.parse(timeIn);
                    Log.d(TAG, "onBindViewHolder: timeInParsing" + timeInParsing);
                    parsedTimeIn = timeFormat2.format(timeInParsing);
                    Log.d(TAG, "onBindViewHolder: parsedTimeIn" + parsedTimeIn);

                    timeOutParsing = timeOutFormat.parse(timeOut);
                    parsedTimeOut = timeOutFormat2.format(timeOutParsing);

                    Log.d(TAG, "onBindViewHolder: duration" + daysDuration);
                    if (daysDuration != null) {
                        durationParsing = duration.parse(daysDuration);
                        parsedDuration = durationParsed.format(durationParsing);
                        Log.d(TAG, "onBindViewHolder: parsedDuration" + parsedDuration);

                        durationInHour = durationHour.parse(daysDuration);
                        durationsHour = Integer.parseInt(durationHour.format(durationInHour));

                        durationsMinutes = Double.parseDouble(durationMinutes.format(durationParsing));
                        Log.d(TAG, "timeDateParser: " + durationMinutes);
                    }

                    sessionCost = (durationsHour + (durationsMinutes/60))  * Constants.HOURLY_COST;
                    //Log.d(TAG, "timeDateParser: " + sessionCost);

                    DecimalFormat formatter = new DecimalFormat("#.##");
                    formattedSessionCost = Double.parseDouble(formatter.format(sessionCost));
                    Log.d(TAG, "timeDateParser: " + formattedSessionCost);

                } catch (ParseException e) {
                }

                dayChecker(childSessionHolder, dayOfWeek, parsedDate, parsedTimeIn, parsedTimeOut, parsedDuration, formattedSessionCost);

                /*timeDateParser(childSessionHolder, dateFormat, dateFormat2, simpleDateFormat, timeFormat, timeFormat2, timeOutFormat, timeOutFormat2, daysDuration,
                        durationParsed, durationHour, durationMinutes, dates);*/
            }
        }
    }

    private void timeDateParser(ChildSessionHolder childSessionHolder, SimpleDateFormat dateFormat, SimpleDateFormat dateFormat2, SimpleDateFormat simpleDateFormat,
                                SimpleDateFormat timeFormat, SimpleDateFormat timeFormat2, SimpleDateFormat timeOutFormat, SimpleDateFormat timeOutFormat2,
                                SimpleDateFormat parsingDuration, SimpleDateFormat durationParsed, SimpleDateFormat durationHour, SimpleDateFormat durationMinutes, ChildSessionModel dates) {


        //dayChecker(childSessionHolder, dayOfWeek, parsedDate, parsedTimeIn, parsedTimeOut, parsedDuration, formattedSessionCost);
    }

    private void dayChecker(ChildSessionHolder childSessionHolder, String dayOfWeek, String parsedDate, String parsedTimeIn, String parsedTimeOut,
                            String parsedDuration, double sessionCost) {
        if (dayOfWeek.equals("Monday")) {
            childSessionHolder.mDate1.setText(parsedDate);
            Log.d(TAG, "dayChecker: " + parsedDate);
            childSessionHolder.mTimeIn1.setText(parsedTimeIn);
            Log.d(TAG, "dayChecker: " + parsedTimeIn);
            childSessionHolder.mMTimeOut.setText(parsedTimeOut);
            childSessionHolder.mMTotalTime.setText(parsedDuration);
            Log.d(TAG, "dayChecker: " + sessionCost);
            childSessionHolder.mMSessionCost.setText(""+sessionCost);
        } else if (dayOfWeek.equals("Tuesday")) {
            childSessionHolder.mDate2.setText(parsedDate);
            childSessionHolder.mTimeIn2.setText(parsedTimeIn);
            childSessionHolder.mTTimeOut.setText(parsedTimeOut);
            childSessionHolder.mTTotalTime.setText(parsedDuration);
            childSessionHolder.mTSessionCost.setText(""+sessionCost);
        } else if (dayOfWeek.equals("Wednesday")) {
            childSessionHolder.mDate3.setText(parsedDate);
            childSessionHolder.mTimeIn3.setText(parsedTimeIn);
            childSessionHolder.mWTimeOut.setText(parsedTimeOut);
            childSessionHolder.mWTotalTime.setText(parsedDuration);
            childSessionHolder.mWSessionCost.setText(""+sessionCost);
        } else if (dayOfWeek.equals("Thursday")) {
            childSessionHolder.mDate4.setText(parsedDate);
            childSessionHolder.mTimeIn4.setText(parsedTimeIn);
            childSessionHolder.mThTimeOut.setText(parsedTimeOut);
            childSessionHolder.mThTotalTime.setText(parsedDuration);
            childSessionHolder.mThSessionCost.setText(""+sessionCost);
        } else if (dayOfWeek.equals("Friday")) {
            childSessionHolder.mDate5.setText(parsedDate);
            childSessionHolder.mTimeIn5.setText(parsedTimeIn);
            childSessionHolder.mFTimeOut.setText(parsedTimeOut);
            childSessionHolder.mFTotalTime.setText(parsedDuration);
            childSessionHolder.mFSessionCost.setText(""+sessionCost);
        }
    }

    @Override
    public int getItemCount() {
        return mChild.size() + mCustomer.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ChildInfoHolder extends ViewHolder {

        TextView mChildName;
        TextView mChildDob;

        public ChildInfoHolder(View itemView) {
            super(itemView);

            mChildName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            mChildDob = (TextView) itemView.findViewById(R.id.tvChildDob);
        }
    }

    public class ChildSessionHolder extends ViewHolder {

        TextView mChildSessionHeading;

        TableLayout mChildSessionsTable;

        TextView mDate1;
        TextView mDate2;
        TextView mDate3;
        TextView mDate4;
        TextView mDate5;

        TextView mTimeIn1;
        TextView mTimeIn2;
        TextView mTimeIn3;
        TextView mTimeIn4;
        TextView mTimeIn5;

        TextView mMTimeOut;
        TextView mTTimeOut;
        TextView mWTimeOut;
        TextView mThTimeOut;
        TextView mFTimeOut;

        TextView mMTotalTime;
        TextView mTTotalTime;
        TextView mWTotalTime;
        TextView mThTotalTime;
        TextView mFTotalTime;

        TextView mMSessionCost;
        TextView mTSessionCost;
        TextView mWSessionCost;
        TextView mThSessionCost;
        TextView mFSessionCost;


        public ChildSessionHolder(View itemView) {
            super(itemView);

            mChildSessionHeading = (TextView) itemView.findViewById(R.id.tvChildSessionsHeading);

            mChildSessionsTable = (TableLayout) itemView.findViewById(R.id.tlChildSessions);

            mDate1 = (TextView) itemView.findViewById(R.id.tvDate1);
            mDate2 = (TextView) itemView.findViewById(R.id.tvDate2);
            mDate3 = (TextView) itemView.findViewById(R.id.tvDate3);
            mDate4 = (TextView) itemView.findViewById(R.id.tvDate4);
            mDate5 = (TextView) itemView.findViewById(R.id.tvDate5);

            mTimeIn1 = (TextView) itemView.findViewById(R.id.tvMTimeIn);
            mTimeIn2 = (TextView) itemView.findViewById(R.id.tvTTimeIn);
            mTimeIn3 = (TextView) itemView.findViewById(R.id.tvWTimeIn);
            mTimeIn4 = (TextView) itemView.findViewById(R.id.tvThTimeIn);
            mTimeIn5 = (TextView) itemView.findViewById(R.id.tvFTimeIn);

            mMTimeOut = (TextView) itemView.findViewById(R.id.tvMTimeOut);
            mTTimeOut = (TextView) itemView.findViewById(R.id.tvTTimeOut);
            mWTimeOut = (TextView) itemView.findViewById(R.id.tvWTimeOut);
            mThTimeOut = (TextView) itemView.findViewById(R.id.tvThTimeOut);
            mFTimeOut = (TextView) itemView.findViewById(R.id.tvFTimeOut);

            mMTotalTime = (TextView) itemView.findViewById(R.id.tvMTotalTime);
            mTTotalTime = (TextView) itemView.findViewById(R.id.tvTTotalTime);
            mWTotalTime = (TextView) itemView.findViewById(R.id.tvWTotalTime);
            mThTotalTime = (TextView) itemView.findViewById(R.id.tvThTotalTime);
            mFTotalTime = (TextView) itemView.findViewById(R.id.tvFTotalTime);

            mMSessionCost = (TextView) itemView.findViewById(R.id.tvMSessionCost);
            mTSessionCost = (TextView) itemView.findViewById(R.id.tvTSessionCost);
            mWSessionCost = (TextView) itemView.findViewById(R.id.tvWSessionCost);
            mThSessionCost = (TextView) itemView.findViewById(R.id.tvThSessionCost);
            mFSessionCost = (TextView) itemView.findViewById(R.id.tvFSessionCost);
        }
    }

    public class CustomerInfoHolder extends ViewHolder {

        TextView mRelationship;
        TextView mName;
        TextView mPhoneNumber;
        TextView mEmail;

        public CustomerInfoHolder(View itemView) {
            super(itemView);

            mRelationship = (TextView) itemView.findViewById(R.id.tvRelationship);
            mName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.tvCustomerCell);
            mEmail = (TextView) itemView.findViewById(R.id.tvCustomerEmail);
        }
    }
}
