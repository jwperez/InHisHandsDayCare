package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.ChildModel;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.util.List;

/**
 * Created by jonathan.perez on 7/26/16.
 */
public class ChildDetailAdapter extends RecyclerView.Adapter<ChildDetailAdapter.ViewHolder> {

    private static final String TAG = ChildDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<CustomerModel> mCustomer;
    private List<ChildModel> mChild;

    public ChildDetailAdapter(Context context, List<CustomerModel> customer, List<ChildModel> child) {
        mContext = context;
        mCustomer = customer;
        mChild = child;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == Constants.CHILD_INFO) {
            view = LayoutInflater.from(mContext).inflate(R.layout.child_info_card, parent, false);
            return new ChildInfoHolder(view);
        } else if (viewType == Constants.CHILD_SESSIONS) {
            view = LayoutInflater.from(mContext).inflate(R.layout.child_sessions_card, parent, false);
            return new ChildSessionHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.customer_detail_card, parent, false);
            return  new CustomerInfoHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == Constants.CHILD_INFO) {
            ChildInfoHolder childInfoHolder = (ChildInfoHolder) holder;
            childInfoHolder.mChildName.setText(mChild.get(position).getLastName());
            childInfoHolder.mChildDob.setText(mChild.get(position).getDob());
        } else if (holder.getItemViewType() == Constants.CHILD_SESSIONS) {
            ChildSessionHolder childSessionHolder = (ChildSessionHolder) holder;

        } else {
            CustomerInfoHolder customerInfoHolder = (CustomerInfoHolder) holder;
            customerInfoHolder.mRelationship.setText(mCustomer.get(position).getRelationshipToChild());
            customerInfoHolder.mName.setText(mCustomer.get(position).getLastName());
            customerInfoHolder.mPhoneNumber.setText(mCustomer.get(position).getPhoneNumber());
            customerInfoHolder.mEmail.setText(mCustomer.get(position).getEmail());
        }

    }

    @Override
    public int getItemCount() {
        return 3;
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

        Spinner mWeek;

        public ChildSessionHolder(View itemView) {
            super(itemView);

            mWeek = (Spinner) itemView.findViewById(R.id.spinner);
        }
    }

    public class CustomerInfoHolder extends ViewHolder {

        TextView mRelationship;
        TextView mName;
        TextView mPhoneNumber;
        TextView mEmail;
        TextView mAddress;

        public CustomerInfoHolder(View itemView) {
            super(itemView);

            mRelationship = (TextView) itemView.findViewById(R.id.tvRelationship);
            mName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.tvCustomerCell);
            mEmail = (TextView) itemView.findViewById(R.id.tvCustomerEmail);
            mAddress = (TextView) itemView.findViewById(R.id.tvCustomerAddress);
        }
    }
}
