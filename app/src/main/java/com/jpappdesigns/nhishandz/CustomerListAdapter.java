package com.jpappdesigns.nhishandz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.util.List;

/**
 * Created by jonathan.perez on 7/20/16.
 */
public class CustomerListAdapter extends RecyclerView.Adapter<MyHolder> {

    private Context mContext;
    List<CustomerModel> customers;

    public CustomerListAdapter(Context context, List<CustomerModel> customers) {
        this.mContext = context;
        this.customers = customers;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
