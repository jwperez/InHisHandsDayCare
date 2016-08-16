package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.ChildModel;
import com.jpappdesigns.nhishandz.model.ChildSessionModel;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jonathan.perez on 8/10/16.
 */
public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    private static final String TAG = ReportsAdapter.class.getSimpleName();
    private Context mContext;
    private List<CustomerModel> mCustomer;
    private List<ChildModel> mChild;
    private List<ChildSessionModel> mChildSessions;
    Double totalHours = 0.00;

    public ReportsAdapter(Context context, List<CustomerModel> customer, List<ChildModel> child, List<ChildSessionModel> childSessions) {
        mContext = context;
        mCustomer = customer;
        mChild = child;
        mChildSessions = childSessions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case Constants.CHILD_INFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.child_info_card, parent, false);
                return new ChildInfoHolder(view);
            case Constants.CHILD_SESSIONS:
                view = LayoutInflater.from(mContext).inflate(R.layout.report_session_item, parent, false);
                return new ChildSessionHolder(view);
            case Constants.PARENT_GUARDIAN_OF_CHILD:
                view = LayoutInflater.from(mContext).inflate(R.layout.customer_detail_card, parent, false);
                return new CustomerInfoHolder(view);
            case Constants.BILL_TOTAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.bill_total_item, parent, false);
                return new BillTotalInfoHolder(view);
            default:
                throw new RuntimeException("viewType ERROR");
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position < mCustomer.size()) {
            return Constants.PARENT_GUARDIAN_OF_CHILD;
        } else if (position < mChild.size() + 1) {
            return Constants.CHILD_INFO;
        } else if (position < mChild.size() + mCustomer.size() + 1) {
            return Constants.CHILD_SESSIONS;
        } else {
            return Constants.BILL_TOTAL;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder.getItemViewType() == Constants.CHILD_INFO) {
            ChildInfoHolder childInfoHolder = (ChildInfoHolder) holder;
            StringBuilder buf = new StringBuilder();
            buf.append(mChild.get(position - 1).getLastName());
            buf.append(", " + mChild.get(position - 1).getFirstName());
            if (!"".equals(mChild.get(position - 1).getMiddleName())) {
                buf.append(" ");
                buf.append(mChild.get(position - 1).getMiddleName());
            } else {
            }
            childInfoHolder.mChildName.setText(buf.toString());
            childInfoHolder.mChildDob.setText(mChild.get(position - 1).getDob());
        } else {
            if (holder.getItemViewType() == Constants.PARENT_GUARDIAN_OF_CHILD) {
                CustomerInfoHolder customerInfoHolder = (CustomerInfoHolder) holder;
                customerInfoHolder.mRelationship.setText(mCustomer.get(position).getRelationshipToChild());

                StringBuilder buf = new StringBuilder();
                buf.append(mCustomer.get(position).getLastName());
                buf.append(", " + mCustomer.get(position).getFirstName());
                if (!"".equals(mCustomer.get(position).getMiddleName())) {
                    buf.append(" ");
                    buf.append(mCustomer.get(position).getMiddleName());
                } else {
                }
                customerInfoHolder.mName.setText(buf.toString());
                customerInfoHolder.mPhoneNumber.setText(mCustomer.get(position).getPhoneNumber());
                customerInfoHolder.mEmail.setText(mCustomer.get(position).getEmail());

            } else if (holder.getItemViewType() == Constants.CHILD_SESSIONS) {
                ChildSessionHolder childSessionHolder = (ChildSessionHolder) holder;
                childSessionHolder.mChildSessionHeading.setText("Sessions");

                TableRow headingRow = new TableRow(mContext);
                headingRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView dateHeading = new TextView(mContext);
                dateHeading.setText("Date");
                dateHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 122, .25f));
                dateHeading.setBackgroundColor(Color.parseColor("#1976D2"));
                dateHeading.setTextColor(Color.parseColor("#FFFFFF"));
                dateHeading.setPadding(0, 20, 0, 0);
                dateHeading.setGravity(1);
                headingRow.addView(dateHeading);

                TextView timeInHeading = new TextView(mContext);
                timeInHeading.setText("Time In");
                timeInHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 122, .25f));
                timeInHeading.setBackgroundColor(Color.parseColor("#1976D2"));
                timeInHeading.setTextColor(Color.parseColor("#FFFFFF"));
                timeInHeading.setPadding(0, 20, 0, 0);
                timeInHeading.setGravity(1);
                headingRow.addView(timeInHeading);

                TextView timeOutHeading = new TextView(mContext);
                timeOutHeading.setText("Time Out");
                timeOutHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 122, .25f));
                timeOutHeading.setBackgroundColor(Color.parseColor("#1976D2"));
                timeOutHeading.setTextColor(Color.parseColor("#FFFFFF"));
                timeOutHeading.setPadding(0, 20, 0, 0);
                timeOutHeading.setGravity(1);
                headingRow.addView(timeOutHeading);

                TextView durationHeading = new TextView(mContext);
                durationHeading.setText("Duration");
                durationHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 122, .25f));
                durationHeading.setBackgroundColor(Color.parseColor("#1976D2"));
                durationHeading.setTextColor(Color.parseColor("#FFFFFF"));
                durationHeading.setPadding(0, 20, 0, 0);
                durationHeading.setGravity(1);
                headingRow.addView(durationHeading);

                /*TextView rightLine = new TextView(mContext, null, R.style.Divider);
                rightLine.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT));
                rightLine.setBackgroundColor(Color.parseColor("#1976D2"));
                rightLine.setBackgroundColor(Color.parseColor("#FFC107"));
                headingRow.addView(rightLine);*/

                TextView SessionCostHeading = new TextView(mContext);
                SessionCostHeading.setText("Session\nCost");
                SessionCostHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                SessionCostHeading.setBackgroundColor(Color.parseColor("#1976D2"));
                SessionCostHeading.setTextColor(Color.parseColor("#FFFFFF"));
                SessionCostHeading.setPadding(0, 20, 0, 0);
                SessionCostHeading.setGravity(1);
                headingRow.addView(SessionCostHeading);

                childSessionHolder.mChildSessionsTable.addView(headingRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                Date formatDate;
                String formatedDate = null;

                SimpleDateFormat initialTimeInFormat = new SimpleDateFormat("hh:mm:ss");
                SimpleDateFormat timeInFormatWanted = new SimpleDateFormat("hh:mm");
                Date timeIn;
                String formatedTimeIn = null;

                SimpleDateFormat initialTimeOutFormat = new SimpleDateFormat("hh:mm:ss");
                SimpleDateFormat timeOutFormatWanted = new SimpleDateFormat("hh:mm");
                Date timeOut;
                String formatedTimeOut = null;

                SimpleDateFormat initialDurationFormat = new SimpleDateFormat("hh:mm:ss");
                SimpleDateFormat durationFormatWanted = new SimpleDateFormat("hh:mm");
                Date duration = null;
                String formatedDuration = null;

                SimpleDateFormat durationHour = new SimpleDateFormat("hh");
                SimpleDateFormat durationMinutes = new SimpleDateFormat("mm");
                int durationsHour = 0;
                double durationsMinutes = 0;

                double sessionCost;
                double formattedSessionCost = 0;

                for (int i = 0; i < mChildSessions.size(); i++) {

                    if ((i % 2) == 0) {
                        TableRow tr = new TableRow(mContext);
                        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                        try {
                            formatDate = dateFormat.parse(mChildSessions.get(i).getDate());
                            formatedDate = sdf.format(formatDate);

                            timeIn = initialTimeInFormat.parse(mChildSessions.get(i).getTimeIn());
                            formatedTimeIn = timeInFormatWanted.format(timeIn);

                            timeOut = initialTimeOutFormat.parse(mChildSessions.get(i).getTimeOut());
                            formatedTimeOut = timeOutFormatWanted.format(timeOut);

                            duration = initialDurationFormat.parse(mChildSessions.get(i).getDuration());
                            formatedDuration = durationFormatWanted.format(duration);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        TextView date = new TextView(mContext);
                        date.setText(formatedDate);
                        date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 50, .25f));
                        //date.setBackgroundColor(Color.parseColor("#BDBDBD"));
                        date.setGravity(1);
                        tr.addView(date);

                        TextView tvTimeIn = new TextView(mContext);
                        tvTimeIn.setText(formatedTimeIn);
                        tvTimeIn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 50, .25f));
                        //tvTimeIn.setBackgroundColor(Color.parseColor("#BDBDBD"));
                        tvTimeIn.setGravity(1);
                        tr.addView(tvTimeIn);

                        TextView tvTimeOut = new TextView(mContext);
                        tvTimeOut.setText(formatedTimeOut);
                        tvTimeOut.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 50, .25f));
                        //tvTimeOut.setBackgroundColor(Color.parseColor("#BDBDBD"));
                        tvTimeOut.setGravity(1);
                        tr.addView(tvTimeOut);

                        TextView tvDuration = new TextView(mContext);
                        tvDuration.setText(formatedDuration);
                        tvDuration.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 50, .25f));
                        //tvDuration.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        tvDuration.setGravity(1);
                        tr.addView(tvDuration);

                        if (duration != null) {
                            durationsHour = Integer.parseInt(durationHour.format(duration));

                            durationsMinutes = Double.parseDouble(durationMinutes.format(duration));
                        }

                        totalHours = totalHours + durationsHour + (durationsMinutes / 60);
                        Log.d(TAG, "onBindViewHolder: totalHours " + totalHours);

                        sessionCost = (durationsHour + (durationsMinutes / 60)) * Constants.HOURLY_COST;
                        //Log.d(TAG, "timeDateParser: " + sessionCost);

                        DecimalFormat formatter = new DecimalFormat("#.##");
                        formattedSessionCost = Double.parseDouble(formatter.format(sessionCost));

                        TextView tvSessionCost = new TextView(mContext);
                        tvSessionCost.setText("$" + formattedSessionCost);
                        tvSessionCost.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                        tvSessionCost.setGravity(1);
                        tr.addView(tvSessionCost);

                        childSessionHolder.mChildSessionsTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                        formatedTimeIn = null;
                        formatedTimeOut = null;
                        formatedDuration = null;
                    } else {
                        TableRow tr = new TableRow(mContext);
                        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                        try {
                            formatDate = dateFormat.parse(mChildSessions.get(i).getDate());
                            formatedDate = sdf.format(formatDate);

                            timeIn = initialTimeInFormat.parse(mChildSessions.get(i).getTimeIn());
                            formatedTimeIn = timeInFormatWanted.format(timeIn);

                            timeOut = initialTimeOutFormat.parse(mChildSessions.get(i).getTimeOut());
                            formatedTimeOut = timeOutFormatWanted.format(timeOut);

                            duration = initialDurationFormat.parse(mChildSessions.get(i).getDuration());
                            formatedDuration = durationFormatWanted.format(duration);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        TextView date = new TextView(mContext);
                        date.setText(formatedDate);
                        date.setLayoutParams(new TableRow.LayoutParams(15, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                        date.setBackgroundColor(Color.parseColor("#BBDEFB"));
                        date.setGravity(1);
                        tr.addView(date);

                        TextView tvTimeIn = new TextView(mContext);
                        tvTimeIn.setText(formatedTimeIn);
                        tvTimeIn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                        tvTimeIn.setBackgroundColor(Color.parseColor("#BBDEFB"));
                        tvTimeIn.setGravity(1);
                        tr.addView(tvTimeIn);

                        TextView tvTimeOut = new TextView(mContext);
                        tvTimeOut.setText(formatedTimeOut);
                        tvTimeOut.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                        tvTimeOut.setBackgroundColor(Color.parseColor("#BBDEFB"));
                        tvTimeOut.setGravity(1);
                        tr.addView(tvTimeOut);

                        TextView tvDuration = new TextView(mContext);
                        tvDuration.setText(formatedDuration);
                        tvDuration.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                        tvDuration.setBackgroundColor(Color.parseColor("#BBDEFB"));
                        tvDuration.setGravity(1);
                        tr.addView(tvDuration);

                        if (duration != null) {
                            durationsHour = Integer.parseInt(durationHour.format(duration));

                            durationsMinutes = Double.parseDouble(durationMinutes.format(duration));
                        }

                        totalHours = totalHours + durationsHour + (durationsMinutes / 60);
                        Log.d(TAG, "onBindViewHolder: totalHours " + totalHours);

                        sessionCost = (durationsHour + (durationsMinutes / 60)) * Constants.HOURLY_COST;
                        //Log.d(TAG, "timeDateParser: " + sessionCost);

                        DecimalFormat formatter = new DecimalFormat("#.##");
                        formattedSessionCost = Double.parseDouble(formatter.format(sessionCost));

                        TextView tvSessionCost = new TextView(mContext);
                        tvSessionCost.setText("$" + formattedSessionCost);
                        tvSessionCost.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                        tvSessionCost.setBackgroundColor(Color.parseColor("#BBDEFB"));
                        tvSessionCost.setGravity(1);
                        tr.addView(tvSessionCost);

                        childSessionHolder.mChildSessionsTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                    }
            }
            } else if (holder.getItemViewType() == Constants.BILL_TOTAL) {

                BillTotalInfoHolder billTotalInfoHolder = (BillTotalInfoHolder) holder;

                String today = null;
                Calendar c = Calendar.getInstance();

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                String todayOutput = dateFormat.format(c.getTime());
                Log.d(TAG, "onBindViewHolder: " + todayOutput);

                billTotalInfoHolder.mStatementDate.setText(todayOutput);

                c.add(Calendar.DAY_OF_MONTH, 14);

                String dueDate = dateFormat.format(c.getTime());
                billTotalInfoHolder.mStatementDueDate.setText(dueDate);

                billTotalInfoHolder.mTotalSessions.setText("" + mChildSessions.size());

                billTotalInfoHolder.mTotalHours.setText("" + totalHours);

                billTotalInfoHolder.mTotal.setText("$"+ (totalHours * Constants.HOURLY_COST));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mChild.size() + mCustomer.size() + 2;
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

            mChildName = (TextView) itemView.findViewById(R.id.tvChildName);
            mChildDob = (TextView) itemView.findViewById(R.id.tvChildDob);
        }
    }

    public class ChildSessionHolder extends ViewHolder {

        TextView mChildSessionHeading;

        TableLayout mChildSessionsTable;

        public ChildSessionHolder(View itemView) {
            super(itemView);

            mChildSessionHeading = (TextView) itemView.findViewById(R.id.tvChildSessionsHeading);

            mChildSessionsTable = (TableLayout) itemView.findViewById(R.id.tlChildSessions);
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

    private class BillTotalInfoHolder extends ViewHolder {

        TextView mStatementDate;
        TextView mStatementDueDate;
        TextView mTotalSessions;
        TextView mTotalHours;
        TextView mCostPerHour;
        TextView mTotal;

        public BillTotalInfoHolder(View view) {
            super(view);

            mStatementDate = (TextView) view.findViewById(R.id.tvStatementDate);
            mStatementDueDate = (TextView) view.findViewById(R.id.tvStatementDueDate);
            mTotalSessions = (TextView) view.findViewById(R.id.tvTotalSessions);
            mTotalHours = (TextView) view.findViewById(R.id.tvTotalHours);
            mCostPerHour = (TextView) view.findViewById(R.id.tvCostPerHour);
            mTotal = (TextView) view.findViewById(R.id.tvSessionsTotal);
        }
    }
}
