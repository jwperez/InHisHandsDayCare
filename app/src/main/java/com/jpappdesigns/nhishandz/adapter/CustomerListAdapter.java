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
import android.widget.Toast;

import com.jpappdesigns.nhishandz.ItemClickListener;
import com.jpappdesigns.nhishandz.MainActivity;
import com.jpappdesigns.nhishandz.MyHolder;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.fragments.CustomerDetailFragment;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import java.util.List;

/**
 * Created by jonathan.perez on 7/20/16.
 */
public class CustomerListAdapter extends RecyclerView.Adapter<MyHolder> {

    private static final String TAG = CustomerListAdapter.class.getSimpleName();
    private Context mContext;
    List<CustomerModel> customers;

    public CustomerListAdapter(Context context, List<CustomerModel> customers) {
        this.mContext = context;
        this.customers = customers;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_card, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: " +customers);
        //Log.d(TAG, "onBindViewHolder: " + customers.sort();
        StringBuilder buf = new StringBuilder();
        buf.append(customers.get(position).getLastName());
        buf.append(", " + customers.get(position).getFirstName());
        if (!"".equals(customers.get(position).getMiddleName())) {
            buf.append(" ");
            buf.append(customers.get(position).getMiddleName());
        } else {
        }

        holder.mCustomerName.setText(buf.toString());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext, customers.get(position).getLastName(), Toast.LENGTH_SHORT).show();

                Fragment fragment = new CustomerDetailFragment();
                Bundle args = new Bundle();
                args.putString("customerId", customers.get(position).getId());
                args.putString("lastName", customers.get(position).getFirstName());
                args.putString("firstName", customers.get(position).getFirstName());
                args.putString("middleName", customers.get(position).getMiddleName());
                args.putString("email", customers.get(position).getEmail());
                args.putString("phoneNumber", customers.get(position).getPhoneNumber());
                args.putString("relationship", customers.get(position).getRelationshipToChild());
                fragment.setArguments(args);
                FragmentTransaction ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
