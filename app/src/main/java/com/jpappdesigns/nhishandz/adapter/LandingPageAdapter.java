package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jpappdesigns.nhishandz.R;

public class LandingPageAdapter extends BaseAdapter {

    private Context mContext;
    private int[] mImages = {R.drawable.ic_reports, R.drawable.ic_reports, R.drawable.ic_reports, R.drawable.ic_reports
    , R.drawable.ic_reports};

    public LandingPageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public Object getItem(int position) {
        return mImages[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mImages[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
}
