package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
    private String[] children;
    private String[] childrenId;

    public SpinnerAdapter(Context context, String[] children, String[] childrenId) {
        super(context, R.layout.spinner_item);
        mContext = context;
        this.children = children;
        Log.d(TAG, "SpinnerAdapter: " + children);
        this.childrenId = childrenId;
        Log.d(TAG, "SpinnerAdapter: " + childrenId);
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
        TextView childId = (TextView) convertView.findViewById(R.id.tvCHildID);


        childId.setText(childrenId[position]);
        childName.setText(children[position]);
        return convertView;
    }
}



