package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jpappdesigns.nhishandz.ItemClickListener;
import com.jpappdesigns.nhishandz.MainActivity;
import com.jpappdesigns.nhishandz.MonthlyReportsActivity;
import com.jpappdesigns.nhishandz.MyViewHolder;
import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.fragments.ChildListFragment;
import com.jpappdesigns.nhishandz.fragments.CustomerListFragment;
import com.jpappdesigns.nhishandz.model.HomescreeenInformation;

import java.util.ArrayList;

public class HomescreenAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    ArrayList<HomescreeenInformation> mData;
    private LayoutInflater mInflater;
    private FragmentTransaction ft;

    public HomescreenAdapter(Context context, ArrayList<HomescreeenInformation> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.card_homescreen, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.getmDescription().setText(mData.get(position).getTitle());
        holder.getmIcon().setImageResource(mData.get(position).getImages());

        holder.setItemClickListener(new ItemClickListener() {

            @Override
            public void onItemClick(int position) {

                Fragment fragment;

                if (position == 0) {
                    fragment = new ChildListFragment();
                    ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (position == 1) {
                    fragment = new CustomerListFragment();
                    ft = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (position == 2) {
                    Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    Toast.makeText(mContext, "Position  " + position, Toast.LENGTH_SHORT).show();
                } else if (position == 4) {
                    Intent reportsActivity = new Intent(mContext, MonthlyReportsActivity.class);
                    mContext.startActivity(reportsActivity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
