package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.util.List;

/**
 * Created by jonathan.perez on 7/26/16.
 */
public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.ViewHolder> {

    private static final String TAG = CustomerDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<CustomerModel> mCustomer;

    public CustomerDetailAdapter(Context context, List<CustomerModel> customer) {
        mContext = context;
        mCustomer = customer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.customer_detail_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ViewHolder customerInfoHolder = (ViewHolder) holder;
        customerInfoHolder.mRelationship.setText(mCustomer.get(position).getRelationshipToChild());
        customerInfoHolder.mName.setText(mCustomer.get(position).getLastName());
        customerInfoHolder.mPhoneNumber.setText(mCustomer.get(position).getPhoneNumber());
        customerInfoHolder.mEmail.setText(mCustomer.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mCustomer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mRelationship;
        TextView mName;
        TextView mPhoneNumber;
        TextView mEmail;
        TextView mAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            mRelationship = (TextView) itemView.findViewById(R.id.tvRelationship);
            mName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.tvCustomerCell);
            mEmail = (TextView) itemView.findViewById(R.id.tvCustomerEmail);
            mAddress = (TextView) itemView.findViewById(R.id.tvCustomerAddress);
        }
    }
}
