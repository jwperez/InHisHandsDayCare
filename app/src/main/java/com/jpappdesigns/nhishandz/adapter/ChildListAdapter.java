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

import com.jpappdesigns.nhishandz.ItemClickListener;
import com.jpappdesigns.nhishandz.MainActivity;
import com.jpappdesigns.nhishandz.MyHolder;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.fragments.ChildDetailFragment;
import com.jpappdesigns.nhishandz.model.ChildModel;

import java.util.List;

/**
 * Created by jonathan.perez on 7/27/16.
 */
public class ChildListAdapter extends RecyclerView.Adapter<MyHolder> {

    private static final String TAG = ChildListAdapter.class.getSimpleName();
    private Context mContext;
    List<ChildModel> mChild;

    public ChildListAdapter(Context context, List<ChildModel> child) {
        mContext = context;
        mChild = child;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_card, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        StringBuilder buf = new StringBuilder();
        buf.append(mChild.get(position).getLastName());
        buf.append(", " + mChild.get(position).getFirstName());
        if (!"".equals(mChild.get(position).getMiddleName())) {
            buf.append(" ");
            buf.append(mChild.get(position).getMiddleName());
        } else {
        }

        holder.mCustomerName.setText(buf.toString());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Fragment fragment = new ChildDetailFragment();
                Bundle args = new Bundle();
                args.putString("childId", mChild.get(position).getId());
                args.putString("customerId", mChild.get(position).getCustomerId());
                Log.d(TAG, "onItemClick: childID " + mChild.get(position).getCustomerId());
                Log.d(TAG, "onItemClick: customerId" + mChild.get(position).getCustomerId());
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
        return mChild.size();
    }
}
