package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
            childSessionHolder.mChildSessionHeading.setText("Sessions");

            TableRow headingRow = new TableRow(mContext);
            headingRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView dateHeading = new TextView(mContext);
            dateHeading.setText("Date");
            dateHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
            dateHeading.setGravity(1);
            headingRow.addView(dateHeading);

            TextView timeInHeading = new TextView(mContext);
            timeInHeading.setText("Time In");
            timeInHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
            timeInHeading.setGravity(1);
            headingRow.addView(timeInHeading);

            TextView timeOutHeading = new TextView(mContext);
            timeOutHeading.setText("Time Out");
            timeOutHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
            timeOutHeading.setGravity(1);
            headingRow.addView(timeOutHeading);

            TextView durationHeading = new TextView(mContext);
            durationHeading.setText("Heading");
            durationHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
            durationHeading.setGravity(1);
            headingRow.addView(durationHeading);

            childSessionHolder.mChildSessionsTable.addView(headingRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for (ChildSessionModel sessions : mChildSessions) {
                TableRow tr = new TableRow(mContext);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView date = new TextView(mContext);
                date.setText(sessions.getDate());
                date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                date.setGravity(1);
                tr.addView(date);

                TextView timeIn = new TextView(mContext);
                timeIn.setText(sessions.getTimeIn());
                timeIn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                timeIn.setGravity(1);
                tr.addView(timeIn);

                TextView timeOut = new TextView(mContext);
                timeOut.setText(sessions.getTimeOut());
                timeOut.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                timeOut.setGravity(1);
                tr.addView(timeOut);

                TextView duration = new TextView(mContext);
                duration.setText(sessions.getDuration());
                duration.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, .25f));
                duration.setGravity(1);
                tr.addView(duration);

            /* Add row to TableLayout. */
                //tr.setBackgroundResource(R.drawable.sf_gradient_03);
                childSessionHolder.mChildSessionsTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
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
}
