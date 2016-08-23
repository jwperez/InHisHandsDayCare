package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.R;

/**
 * Created by jonathan.perez on 8/21/16.
 */
public class CustomerSpinnerAdapter extends ArrayAdapter<String> {

    private static final String TAG = SpinnerAdapter.class.getSimpleName();
    private Context mContext;
    private String[] mCustomer;
    private String[] mCustomerId;

    public CustomerSpinnerAdapter(Context context, String[] customer, String[] cusotmerId) {
        super(context, R.layout.spinner_customer_item, customer);
        mContext = context;
        this.mCustomer = customer;
        mCustomerId = cusotmerId;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView);
    }

    @NonNull
    private View initView(int position, View convertView) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_customer_item, null);
        }

        TextView childName = (TextView) convertView.findViewById(R.id.tvCustomerName);
        TextView customerId = (TextView) convertView.findViewById(R.id.tvCustomerId);

        childName.setText(mCustomer[position]);
        customerId.setText(mCustomerId[position]);
        return convertView;
    }
}