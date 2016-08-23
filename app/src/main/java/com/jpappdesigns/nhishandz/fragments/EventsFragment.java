package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpappdesigns.nhishandz.Constants;
import com.jpappdesigns.nhishandz.GetEventsBackgroundWorker;
import com.jpappdesigns.nhishandz.R;

/**
 * Created by jonathan.perez on 8/17/16.
 */
public class EventsFragment extends Fragment {

    private static final String TAG = EventsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvEventsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                animate(view);
            }
        });
        getData();

        return view;

    }

    public void animate(View view) {

        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion));
        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));


        Fragment addEvent = new AddEventFragment();
        addEvent.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion));
        addEvent.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));

        View viewStart = view.findViewById(R.id.fab);

        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_frame, addEvent)
                .addToBackStack("transition")
                .addSharedElement(viewStart, "hello");

        ft.commit();
    }

    private void getData() {
        GetEventsBackgroundWorker d = new GetEventsBackgroundWorker(getActivity(), Constants.GET_EVENTS, mRecyclerView);
        d.execute();
    }
}
