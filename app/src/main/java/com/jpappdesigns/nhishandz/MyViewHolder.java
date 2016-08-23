package com.jpappdesigns.nhishandz;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jonathan.perez on 7/23/16.
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mDescription;
    private ImageView mIcon;
    ItemClickListener mItemClickListener;

    public MyViewHolder(View itemView) {
        super(itemView);

        mDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        mIcon = (ImageView) itemView.findViewById(R.id.ivIcon);

        itemView.setOnClickListener(this);


    }

    public TextView getmDescription() {
        return mDescription;
    }

    public void setmDescription(TextView mDescription) {
        this.mDescription = mDescription;
    }

    public ImageView getmIcon() {
        return mIcon;
    }

    public void setmIcon(ImageView mIcon) {
        this.mIcon = mIcon;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        mItemClickListener.onItemClick(getLayoutPosition());
    }


}
