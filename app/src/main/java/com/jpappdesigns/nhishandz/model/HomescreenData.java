package com.jpappdesigns.nhishandz.model;

import com.jpappdesigns.nhishandz.R;

import java.util.ArrayList;

/**
 * Created by jonathan.perez on 7/23/16.
 */
public class HomescreenData {

    public static ArrayList<HomescreeenInformation> getData() {

        ArrayList<HomescreeenInformation> data = new ArrayList<>();

        int[] images = {
                R.drawable.ic_children,
                R.drawable.ic_parents,
                R.drawable.ic_record_a_session,
                R.drawable.ic_upcoming_events,
                R.drawable.ic_reports,
        };

        String[] categories = {"Children", "Customers", "Record a Session", "Upcoming Events", "Reports"};

        for (int i = 0; i < images.length; i++) {

            HomescreeenInformation homescreeenInformation = new HomescreeenInformation();
            homescreeenInformation.setTitle(categories[i]);
            homescreeenInformation.setImages(images[i]);
            data.add(homescreeenInformation);
        }

        return data;
    }


}
