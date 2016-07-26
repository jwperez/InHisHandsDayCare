package com.jpappdesigns.nhishandz;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
/**
 * Created by jonathan.perez on 7/20/16.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mCustomerName;
    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        mCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition());
    }
}
