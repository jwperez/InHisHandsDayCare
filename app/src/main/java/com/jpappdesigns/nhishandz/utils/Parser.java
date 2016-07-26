package com.jpappdesigns.nhishandz.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jpappdesigns.nhishandz.adapter.CustomerDetailAdapter;
import com.jpappdesigns.nhishandz.adapter.CustomerListAdapter;
import com.jpappdesigns.nhishandz.model.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jonathan.perez on 7/20/16.
 */
public class Parser extends AsyncTask<Void, Integer, Integer> {

    private static final String TAG = Parser.class.getSimpleName();
    private Context mContext;
    private String data;
    private ProgressDialog mProgressDialog;
    private ArrayList<CustomerModel> customers = new ArrayList<>();
    private RecyclerView mRecyclerView;
    CustomerListAdapter mCustomerListAdapter;
    CustomerDetailAdapter mCustomerDetailAdapter;

    private String mParsingFor;

    public Parser(Context context, String data, RecyclerView recyclerView, String parsingFor) {
        mContext = context;
        this.data = data;
        mRecyclerView = recyclerView;
        mParsingFor = parsingFor;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Parsing Data");
        mProgressDialog.setMessage("Parsing....Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        Log.d(TAG, "doInBackground: " + mParsingFor);
        if (mParsingFor.equals("All Customers")) {
            return this.parse();
        } else if (mParsingFor.equals("All Children")) {
            return this.parseChildren();
        } else if (mParsingFor.equals("Single Customer")) {
            return this.parseForSingleCustomer();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        mProgressDialog.dismiss();

        if (mParsingFor.equals("Single Customer")) {
            if (integer == 1) {
                mCustomerDetailAdapter = new CustomerDetailAdapter(mContext, customers);
                mRecyclerView.setAdapter(mCustomerDetailAdapter);
            } else {
                Toast.makeText(mContext, "Unable to parse customer data.", Toast.LENGTH_LONG).show();
            }
        } else if(mParsingFor.equals("All Customers") || mParsingFor.equals("All Children")) {
            if (integer == 1) {
                Log.d(TAG, "onPostExecute: " + mParsingFor);
                mCustomerListAdapter = new CustomerListAdapter(mContext, customers);
                mRecyclerView.setAdapter(mCustomerListAdapter);
            } else {
                Toast.makeText(mContext, "Unable to parse customer data.", Toast.LENGTH_LONG).show();
            }
        }



        /*if (integer == 1) {
            if (mParsingFor.equals("Single Customer")) {

            } else {
                Log.d(TAG, "onPostExecute: " + mParsingFor);
                mCustomerListAdapter = new CustomerListAdapter(mContext, customers);
                mRecyclerView.setAdapter(mCustomerListAdapter);
            }
        } else {
            Toast.makeText(mContext, "Unable to parse customer data.", Toast.LENGTH_LONG).show();
        }*/
    }

    private int parse() {

        try {
            JSONArray customer = null;
            JSONObject jsonObject = new JSONObject(data);
            customer = jsonObject.getJSONArray("customer");

            customers.clear();

            for (int i = 0; i < customer.length(); i++) {
                JSONObject c = customer.getJSONObject(i);
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(c.getString("customerId"));
                customerModel.setLastName(c.getString("lastName"));
                customerModel.setFirstName(c.getString("firstName"));
                customerModel.setMiddleName(c.getString("middleName"));
                customerModel.setEmail(c.getString("email"));
                customerModel.setPhoneNumber(c.getString("phoneNumber"));
                customerModel.setRelationshipToChild(c.getString("relationshipToChild"));
                customers.add(customerModel);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int parseForSingleCustomer() {

        try {
            JSONArray customer = null;
            JSONObject jsonObject = new JSONObject(data);
            customer = jsonObject.getJSONArray("customer");

            customers.clear();

            for (int i = 0; i < customer.length(); i++) {
                JSONObject c = customer.getJSONObject(i);
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(c.getString("customerId"));
                customerModel.setLastName(c.getString("lastName"));
                customerModel.setFirstName(c.getString("firstName"));
                customerModel.setMiddleName(c.getString("middleName"));
                customerModel.setEmail(c.getString("email"));
                customerModel.setPhoneNumber(c.getString("phoneNumber"));
                customerModel.setRelationshipToChild(c.getString("relationshipToChild"));
                customers.add(customerModel);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int parseChildren() {

        try {
            JSONArray children = null;
            JSONObject jsonObject = new JSONObject(data);
            children = jsonObject.getJSONArray("child");

            customers.clear();

            for (int i = 0; i < children.length(); i++) {
                JSONObject c = children.getJSONObject(i);
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(c.getString("childId"));
                customerModel.setLastName(c.getString("lastName"));
                customerModel.setFirstName(c.getString("firstName"));
                customerModel.setMiddleName(c.getString("middleName"));
                customerModel.setEmail(c.getString("dob"));
                customers.add(customerModel);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
