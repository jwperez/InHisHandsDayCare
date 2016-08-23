package com.jpappdesigns.nhishandz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.model.EventsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jonathan.perez on 8/17/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private static final String TAG = EventsAdapter.class.getSimpleName();
    private Context mContext;
    List<EventsModel> mEvents;

    public EventsAdapter(Context context, List<EventsModel> events) {
        mContext = context;
        mEvents = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date formatDate;
        String formatedDate = null;

        TableRow headingRow = new TableRow(mContext);
        headingRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView dateHeading = new TextView(mContext);
        dateHeading.setText("Date");
        dateHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 115, .5f));
        dateHeading.setBackgroundColor(Color.parseColor("#1976D2"));
        dateHeading.setTextColor(Color.parseColor("#FFFFFF"));
        dateHeading.setPadding(0, 20, 0, 0);
        dateHeading.setGravity(1);
        headingRow.addView(dateHeading);

        TextView timeInHeading = new TextView(mContext);
        timeInHeading.setText("Event");
        timeInHeading.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 115, .5f));
        timeInHeading.setBackgroundColor(Color.parseColor("#1976D2"));
        timeInHeading.setTextColor(Color.parseColor("#FFFFFF"));
        timeInHeading.setPadding(0, 20, 0, 0);
        timeInHeading.setGravity(1);
        headingRow.addView(timeInHeading);

        holder.mEvents.addView(headingRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < mEvents.size(); i++) {

            if ((i % 2) == 0) {
                TableRow tr = new TableRow(mContext);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                try {
                    formatDate = dateFormat.parse(mEvents.get(i).getEventDate());
                    formatedDate = sdf.format(formatDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView date = new TextView(mContext);
                date.setText(formatedDate);
                date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 100, .5f));
                //date.setBackgroundColor(Color.parseColor("#BDBDBD"));
                date.setGravity(1);
                tr.addView(date);


                TextView eventName = new TextView(mContext);
                eventName.setText(mEvents.get(i).getEventsName());
                eventName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 100, .5f));
                eventName.setGravity(1);
                tr.addView(eventName);


                holder.mEvents.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            } else {
                TableRow tr = new TableRow(mContext);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                try {
                    formatDate = dateFormat.parse(mEvents.get(i).getEventDate());
                    formatedDate = sdf.format(formatDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView date = new TextView(mContext);
                date.setText(formatedDate);
                date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 100, .25f));
                date.setBackgroundColor(Color.parseColor("#BBDEFB"));
                date.setGravity(1);
                tr.addView(date);

                TextView tvDuration = new TextView(mContext);
                tvDuration.setText(mEvents.get(i).getEventsName());
                tvDuration.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 100, .25f));
                tvDuration.setBackgroundColor(Color.parseColor("#BBDEFB"));
                tvDuration.setGravity(1);
                tr.addView(tvDuration);

                holder.mEvents.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TableLayout mEvents;

        public ViewHolder(View itemView) {
            super(itemView);

            mEvents = (TableLayout) itemView.findViewById(R.id.tlEvents);
        }
    }
}
