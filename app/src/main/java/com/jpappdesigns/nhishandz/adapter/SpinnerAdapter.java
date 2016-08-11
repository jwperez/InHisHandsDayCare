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
 * Created by jonathan.perez on 8/8/16.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private static final String TAG = SpinnerAdapter.class.getSimpleName();
    private Context mContext;
    private String[] mChildren;
    private String[] mChildrenId;
    private String[] mCustomerId;

    public SpinnerAdapter(Context context, String[] children, String[] childrenId, String[] cusotmerId) {
        super(context, R.layout.spinner_item,children);
        mContext = context;
        this.mChildren = children;
        this.mChildrenId = childrenId;
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
            convertView = inflater.inflate(R.layout.spinner_item, null);
        }

        TextView childName = (TextView) convertView.findViewById(R.id.tvChildName);
        TextView childId = (TextView) convertView.findViewById(R.id.tvChildID);
        TextView customerId = (TextView) convertView.findViewById(R.id.tvCustomerId);


        childId.setText(mChildrenId[position]);
        childName.setText(mChildren[position]);
        customerId.setText(mCustomerId[position]);
        return convertView;
    }
}



