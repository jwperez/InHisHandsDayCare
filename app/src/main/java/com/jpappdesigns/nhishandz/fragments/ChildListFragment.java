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
import com.jpappdesigns.nhishandz.CustomerDownloader;
import com.jpappdesigns.nhishandz.R;

/**
 * Created by jonathan.perez on 7/23/16.
 */
public class ChildListFragment extends Fragment {

    private static final String TAG = ChildListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCustomerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setTransitionName("reveal");
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animateIntent(view);
            }
        });
        getData();

        return view;
    }

    public void animateIntent(View view) {

        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion));
        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));


        Fragment addCustomer = new AddChildFragment();
        addCustomer.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.changebounds_with_arcmotion));
        addCustomer.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));

        View viewStart = view.findViewById(R.id.fab);

        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_frame, addCustomer)
                .addToBackStack("transition")
                .addSharedElement(viewStart, "hello");

        ft.commit();



        /*// Ordinary Intent for launching a new activity
        Intent intent = new Intent(getActivity(), AddChildFragment.class);

        // Get the transition name from the string
        String transitionName = getString(R.string.transition_string);

        // Define the view that the animation will start from
        View viewStart = view.findViewById(R.id.fab);

        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewStart,   // Starting view
                        transitionName    // The String
                );
        //Start the Intent
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());*/

    }

    private void getData() {
        CustomerDownloader d = new CustomerDownloader(getActivity(), Constants.GET_CHILDREN_URL, mRecyclerView, TAG);
        d.execute();
    }
}
