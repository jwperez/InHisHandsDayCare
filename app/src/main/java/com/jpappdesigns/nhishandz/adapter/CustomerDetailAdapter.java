package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.MainActivity;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.fragments.UpdateChildFragment;
import com.jpappdesigns.nhishandz.fragments.UpdateCustomerFragment;
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
    private FragmentTransaction ft;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

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

            customerInfoHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Fragment fragment = new UpdateCustomerFragment();
                    Bundle args = new Bundle();
                    args.putString("CustomerId", mCustomer.get(position).getId());
                    args.putString("CustomerLastName", mCustomer.get(position).getLastName());
                    args.putString("CustomerFirstName", mCustomer.get(position).getFirstName());
                    args.putString("CustomerMiddleName", mCustomer.get(position).getMiddleName());
                    args.putString("CustomerEmail", mCustomer.get(position).getEmail());
                    args.putString("CustomerPhoneNumber", mCustomer.get(position).getPhoneNumber());
                    args.putString("CustomerRelationship", mCustomer.get(position).getRelationshipToChild());
                    fragment.setArguments(args);
                    ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                }
            });


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

            childInfoHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Fragment fragment = new UpdateChildFragment();
                    Bundle args = new Bundle();
                    args.putString("ChildId", mChild.get(position - 1).getId());
                    args.putString("ChildLastName", mChild.get(position - 1).getLastName());
                    args.putString("ChildFirstName", mChild.get(position - 1).getFirstName());
                    args.putString("ChildMiddleName", mChild.get(position - 1).getMiddleName());
                    args.putString("ChildDob", mChild.get(position - 1).getDob());
                    fragment.setArguments(args);
                    ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                }
            });
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

    public class CustomerInfoHolder extends ViewHolder implements View.OnLongClickListener {

        TextView mRelationship;
        TextView mName;
        TextView mPhoneNumber;
        TextView mEmail;
        View.OnLongClickListener mOnLongClickListener;

        public CustomerInfoHolder(View itemView) {
            super(itemView);

            mRelationship = (TextView) itemView.findViewById(R.id.tvRelationship);
            mName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.tvCustomerCell);
            mEmail = (TextView) itemView.findViewById(R.id.tvCustomerEmail);

            itemView.setOnLongClickListener(this);
        }

        public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
            mOnLongClickListener = onLongClickListener;
        }

        @Override
        public boolean onLongClick(View view) {
            mOnLongClickListener.onLongClick(itemView);
            return true;
        }
    }

    public class ChildInfoHolder extends ViewHolder implements View.OnLongClickListener {

        TextView mChildName;
        TextView mChildDob;
        View.OnLongClickListener mOnLongClickListener;

        public ChildInfoHolder(View itemView) {
            super(itemView);

            mChildName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            mChildDob = (TextView) itemView.findViewById(R.id.tvChildDob);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            mOnLongClickListener.onLongClick(view);
            return true;
        }
    }
}
