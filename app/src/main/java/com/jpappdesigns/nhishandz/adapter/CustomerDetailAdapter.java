package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.ChildModel;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jonathan.perez on 7/26/16.
 */
public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.ViewHolder> {

    private static final String TAG = CustomerDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<CustomerModel> mCustomer;
    private List<ChildModel> mChild;

    public CustomerDetailAdapter(Context context, List<CustomerModel> customer, List<ChildModel> child) {
        mContext = context;
        mCustomer = customer;
        mChild = child;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case Constants.CUSTOMER_INFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.customer_detail_card, parent, false);
                return new CustomerInfoHolder(view);
            case Constants.CHILD_OF_PARENT_INFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.child_info_card, parent, false);
                return new ChildInfoHolder(view);
            default:
                throw new RuntimeException("viewType ERROR");
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Constants.CUSTOMER_INFO;
        } else if (position >= 1) {
            return Constants.CHILD_OF_PARENT_INFO;
        } else {
            throw new RuntimeException("getItemViewType ERROR ");
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Calendar calender = Calendar.getInstance();
        Log.d(TAG, "onBindViewHolder: " + calender.get(Calendar.WEEK_OF_YEAR));

        if (position < mCustomer.size()) {
            CustomerInfoHolder customerInfoHolder = (CustomerInfoHolder) holder;
            customerInfoHolder.mRelationship.setText(mCustomer.get(position).getRelationshipToChild());
            customerInfoHolder.mPhoneNumber.setText(mCustomer.get(position).getPhoneNumber());
            customerInfoHolder.mEmail.setText(mCustomer.get(position).getEmail());

            StringBuilder buf = new StringBuilder();
            buf.append(mCustomer.get(position).getLastName());
            buf.append(", " + mCustomer.get(position).getFirstName());
            if (!"".equals(mCustomer.get(position).getMiddleName())) {
                buf.append(" ");
                buf.append(mCustomer.get(position).getMiddleName());
            } else {
            }
            customerInfoHolder.mName.setText(buf.toString());

        } else {
            ChildInfoHolder childInfoHolder = (ChildInfoHolder) holder;
            childInfoHolder.mChildDob.setText(mChild.get(position - 1).getDob());

            StringBuilder buf = new StringBuilder();
            buf.append(mChild.get(position -1).getLastName());
            buf.append(", " + mChild.get(position -1).getFirstName());
            if (!"".equals(mChild.get(position -1).getMiddleName())) {
                buf.append(" ");
                buf.append(mChild.get(position -1).getMiddleName());
            } else {
            }

            childInfoHolder.mChildName.setText(buf.toString());
        }


    }

    @Override
    public int getItemCount() {
        return mCustomer.size() + mChild.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
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

    public class ChildInfoHolder extends ViewHolder {

        TextView mChildName;
        TextView mChildDob;

        public ChildInfoHolder(View itemView) {
            super(itemView);

            mChildName = (TextView) itemView.findViewById(R.id.tvChildName);
            mChildDob = (TextView) itemView.findViewById(R.id.tvChildDob);
        }
    }
}
