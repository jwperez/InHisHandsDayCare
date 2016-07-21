package com.jpappdesigns.nhishandz;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.R;
/**
 * Created by jonathan.perez on 7/20/16.
 */
public class MyHolder extends RecyclerView.ViewHolder {

    private TextView mCustomerName;

    public MyHolder(View itemView) {
        super(itemView);

        mCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);
    }
}
