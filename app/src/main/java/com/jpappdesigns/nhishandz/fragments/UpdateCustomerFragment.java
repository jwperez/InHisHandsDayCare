package com.jpappdesigns.nhishandz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.UpdateCustomerBackgroundWorker;
import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

/**
 * Created by jonathan.perez on 8/22/16.
 */
public class UpdateCustomerFragment extends Fragment {

    private static final String TAG = UpdateCustomerFragment.class.getSimpleName();
    private String mCustomerId;
    private String mCustomerLastName;
    private String mCustomerFirstName;
    private String mCustomerMiddleName;
    private String mCustomerEmail;
    private String mCustomerPhoneNumber;
    private String mCustomerRelationship;
    private String mResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mCustomerId = getArguments().getString("CustomerId");
        mCustomerLastName = getArguments().getString("CustomerLastName");
        mCustomerFirstName = getArguments().getString("CustomerFirstName");
        mCustomerMiddleName = getArguments().getString("CustomerMiddleName");
        mCustomerEmail = getArguments().getString("CustomerEmail");
        mCustomerPhoneNumber = getArguments().getString("CustomerPhoneNumber");
        mCustomerRelationship = getArguments().getString("CustomerRelationship");

        View view = inflater.inflate(R.layout.update_customer, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        final EditText customerLastName = (EditText) view.findViewById(R.id.etLastName);
        final EditText customerFirstName = (EditText) view.findViewById(R.id.etFirstName);
        final EditText customerMiddleName = (EditText) view.findViewById(R.id.etMiddleName);
        final EditText customerEmail = (EditText) view.findViewById(R.id.etEmail);
        final EditText customerPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);
        final EditText customerRelationship = (EditText) view.findViewById(R.id.etRelationship);
        Button mUpdateCustomer = (Button) view.findViewById(R.id.btnUpdateCustomer);

        customerLastName.setText(mCustomerLastName);
        customerFirstName.setText(mCustomerFirstName);
        customerMiddleName.setText(mCustomerMiddleName);
        customerEmail.setText(mCustomerEmail);
        customerPhoneNumber.setText(mCustomerPhoneNumber);
        customerRelationship.setText(mCustomerRelationship);

        mUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                UpdateCustomerBackgroundWorker updateCustomerBackgroundWorker = new UpdateCustomerBackgroundWorker(getActivity(), mCustomerId, customerLastName.getText().toString(), customerFirstName.getText().toString(),
                        customerMiddleName.getText().toString(), customerEmail.getText().toString(), customerPhoneNumber.getText().toString(),
                        customerRelationship.getText().toString(), new AsyncStringResult() {

                    @Override
                    public void onResult(String object) {
                        mResult = object;

                        Log.d(TAG, "onResult: " + mResult);
                        if (mResult.equals("Customer Updated Successfully")) {
                            FragmentManager fm = getFragmentManager();
                            Snackbar.make(view, "Customer successfully updated in database", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            fm.popBackStackImmediate();

                        } else {
                            Snackbar.make(view, "Sorry, but there seems to be a problem with your inputs.  Please verify them and try again.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
                updateCustomerBackgroundWorker.execute();
            }
        });
    }
}
