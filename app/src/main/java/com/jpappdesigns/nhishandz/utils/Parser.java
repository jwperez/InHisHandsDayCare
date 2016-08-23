package com.jpappdesigns.nhishandz.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.jpappdesigns.nhishandz.R;
import com.jpappdesigns.nhishandz.adapter.ChildDetailAdapter;
import com.jpappdesigns.nhishandz.adapter.ChildListAdapter;
import com.jpappdesigns.nhishandz.adapter.CustomerDetailAdapter;
import com.jpappdesigns.nhishandz.adapter.CustomerListAdapter;
import com.jpappdesigns.nhishandz.adapter.CustomerSpinnerAdapter;
import com.jpappdesigns.nhishandz.adapter.EventsAdapter;
import com.jpappdesigns.nhishandz.adapter.ReportsAdapter;
import com.jpappdesigns.nhishandz.adapter.SpinnerAdapter;
import com.jpappdesigns.nhishandz.model.ChildModel;
import com.jpappdesigns.nhishandz.model.ChildSessionModel;
import com.jpappdesigns.nhishandz.model.CustomerModel;
import com.jpappdesigns.nhishandz.model.EventsModel;

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
    private String data2;
    private String data3;
    private ProgressDialog mProgressDialog;
    private ArrayList<CustomerModel> customers = new ArrayList<>();
    private ArrayList<ChildModel> child = new ArrayList<>();
    private ArrayList<EventsModel> event = new ArrayList<>();
    private ArrayList<ChildSessionModel> mChildSessionModels= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Spinner mChildrenSpinner;
    private Spinner mCustomerSpinner;
    CustomerListAdapter mCustomerListAdapter;
    ChildDetailAdapter mChildDetailAdapter;
    ChildListAdapter mChildListAdapter;
    CustomerDetailAdapter mCustomerDetailAdapter;
    ReportsAdapter mReportsAdapter;
    private EventsAdapter mEventsAdapter;
    private String mExtra;

    private String mParsingFor;

    public Parser(Context context, String data, String parsingFor) {
        mContext = context;
        this.data3 = data;
        mParsingFor = parsingFor;
    }

    public Parser(Context context, String data, RecyclerView recyclerView, String parsingFor) {
        mContext = context;
        this.data = data;
        mRecyclerView = recyclerView;
        mParsingFor = parsingFor;
    }

    public Parser(Context context, String data, Spinner spinner, String parsingFor) {
        Log.d(TAG, "Parser: no extra");
        mContext = context;
        this.data = data;
        Log.d(TAG, "Parser: no extra data " + data);
        mChildrenSpinner = spinner;
        mParsingFor = parsingFor;
    }

    public Parser(Context context, String data, Spinner spinner, String parsingFor, String extra) {
        Log.d(TAG, "Parser: with extra");
        mContext = context;
        this.data = data;
        Log.d(TAG, "Parser: with extra data " + data);
        mCustomerSpinner = spinner;
        Log.d(TAG, "Parser: " + mChildrenSpinner);
        mParsingFor = parsingFor;
        mExtra = extra;
    }

    public Parser(Context context, String data, String data2, RecyclerView recyclerView, String parsingFor) {
        mContext = context;
        this.data = data;
        this.data2 = data2;
        mRecyclerView = recyclerView;
        mParsingFor = parsingFor;
    }

    public Parser(Context context, String data, String data2, String data3, RecyclerView recyclerView, String parsingFor) {
        mContext = context;
        this.data = data;
        this.data2 = data2;
        this.data3 = data3;
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
            this.parseParentChild();
            return this.parseForSingleCustomer();
        } else if (mParsingFor.equals("Child Details")) {
            this.parseForSingleCustomer();
            this.parseChildSessions();
            return this.parseChildById();
        } else if (mParsingFor.equals("MonthlyReportsFragment")) {
            return this.parseChildren();
        } else if (mParsingFor.equals("MonthlyReportsPrintoutFragment")) {
            this.parseForSingleCustomer();
            this.parseChildSessions();
            return this.parseChildById();
        } else if (mParsingFor.equals("RecordSessionFragment")) {
            return this.parseChildren();
        } else if (mParsingFor.equals("UpdateSessionFragment")) {
            return this.parseChildren();
        } else if (mParsingFor.equals("Child Session")) {
            return this.parseChildSessions();
        } else if (mParsingFor.equals("Events")) {
            return this.parseEvents();
        } else if (mParsingFor.equals("AddChildFragment")) {
            return this.parse();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        mProgressDialog.dismiss();

        Log.d(TAG, "onPostExecute: " +mParsingFor);

        if (mParsingFor.equals("Single Customer")) {
            if (integer == 1) {
                mCustomerDetailAdapter = new CustomerDetailAdapter(mContext, customers, child);
                mRecyclerView.setAdapter(mCustomerDetailAdapter);
            } else {
                Toast.makeText(mContext, "Unable to parse customer data.", Toast.LENGTH_LONG).show();
            }
        } else if(mParsingFor.equals("All Customers")) {
            if (integer == 1) {
                mCustomerListAdapter = new CustomerListAdapter(mContext, customers);
                mRecyclerView.setAdapter(mCustomerListAdapter);
            } else {
                Toast.makeText(mContext, "Unable to parse customer data.", Toast.LENGTH_LONG).show();
            }
        } else if (mParsingFor.equals("All Children")) {
            if (integer == 1) {
                mChildListAdapter = new ChildListAdapter(mContext, child);
                mRecyclerView.setAdapter(mChildListAdapter);
            }
        } else if (mParsingFor.equals("Child Details")) {
            mChildDetailAdapter = new ChildDetailAdapter(mContext, customers, child, mChildSessionModels);
            mRecyclerView.setAdapter(mChildDetailAdapter);
        } else if (mParsingFor.equals("MonthlyReportsFragment")) {
            String[] labels = new String[child.size()];
            String[] childId = new String[child.size()];
            String[] customerId = new String[child.size()];

            for (int i = 0; i < child.size(); i++) {

                StringBuilder buf = new StringBuilder();
                buf.append(child.get(i).getLastName());
                buf.append(", " + child.get(i).getFirstName());
                if (!"".equals(child.get(i).getMiddleName())) {
                    buf.append(" ");
                    buf.append(child.get(i).getMiddleName());
                } else {
                }

                labels[i] =buf.toString();
                childId[i] = child.get(i).getId();
                customerId[i] = child.get(i).getCustomerId();
                Log.d(TAG, "onPostExecute: " + customerId[i]);

            }

            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(mContext, labels, childId, customerId);

            spinnerAdapter
                    .setDropDownViewResource(R.layout.spinner_item);

            mChildrenSpinner.setAdapter(spinnerAdapter);
        } else if (mParsingFor.equals("MonthlyReportsPrintoutFragment")) {
            mReportsAdapter = new ReportsAdapter(mContext, customers, child, mChildSessionModels);
            mRecyclerView.setAdapter(mReportsAdapter);
        } else if (mParsingFor.equals("RecordSessionFragment")) {
            String[] labels = new String[child.size()];
            String[] childId = new String[child.size()];
            String[] customerId = new String[child.size()];

            for (int i = 0; i < child.size(); i++) {

                StringBuilder buf = new StringBuilder();
                buf.append(child.get(i).getLastName());
                buf.append(", " + child.get(i).getFirstName());
                if (!"".equals(child.get(i).getMiddleName())) {
                    buf.append(" ");
                    buf.append(child.get(i).getMiddleName());
                } else {
                }

                labels[i] =buf.toString();
                childId[i] = child.get(i).getId();
                customerId[i] = child.get(i).getCustomerId();
                Log.d(TAG, "onPostExecute: " + customerId[i]);

            }

            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(mContext, labels, childId, customerId);

            spinnerAdapter
                    .setDropDownViewResource(R.layout.spinner_item);

            mChildrenSpinner.setAdapter(spinnerAdapter);
        } else if (mParsingFor.equals("UpdateSessionFragment")) {
            String[] labels = new String[child.size()];
            String[] childId = new String[child.size()];
            String[] customerId = new String[child.size()];

            for (int i = 0; i < child.size(); i++) {

                StringBuilder buf = new StringBuilder();
                buf.append(child.get(i).getLastName());
                buf.append(", " + child.get(i).getFirstName());
                if (!"".equals(child.get(i).getMiddleName())) {
                    buf.append(" ");
                    buf.append(child.get(i).getMiddleName());
                } else {
                }

                labels[i] =buf.toString();
                childId[i] = child.get(i).getId();
                customerId[i] = child.get(i).getCustomerId();
                Log.d(TAG, "onPostExecute: " + customerId[i]);

            }

            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(mContext, labels, childId, customerId);

            spinnerAdapter
                    .setDropDownViewResource(R.layout.spinner_item);

            mChildrenSpinner.setAdapter(spinnerAdapter);
        } else if (mParsingFor.equals("Events")) {
            if (integer == 1) {
                mEventsAdapter = new EventsAdapter(mContext, event);
                mRecyclerView.setAdapter(mEventsAdapter);
            }
        } else if (mParsingFor.equals("AddChildFragment")) {
            if (integer == 1) {
                String[] labels = new String[customers.size()];
                String[] customerId = new String[customers.size()];

                for (int i = 0; i < customers.size(); i++) {

                    StringBuilder buf = new StringBuilder();
                    buf.append(customers.get(i).getLastName());
                    buf.append(", " + customers.get(i).getFirstName());
                    if (!"".equals(customers.get(i).getMiddleName())) {
                        buf.append(" ");
                        buf.append(customers.get(i).getMiddleName());
                    } else {
                    }

                    labels[i] =buf.toString();
                    customerId[i] = customers.get(i).getId();
                    Log.d(TAG, "onPostExecute: " + customerId[i]);

                }

                CustomerSpinnerAdapter spinnerAdapter = new CustomerSpinnerAdapter(mContext, labels, customerId);

                spinnerAdapter
                        .setDropDownViewResource(R.layout.spinner_customer_item);

                mCustomerSpinner.setAdapter(spinnerAdapter);
            }
        }
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
                ChildModel childModel = new ChildModel();
                childModel.setId(c.getString("childId"));
                childModel.setLastName(c.getString("lastName"));
                childModel.setFirstName(c.getString("firstName"));
                childModel.setMiddleName(c.getString("middleName"));
                childModel.setDob(c.getString("dob"));
                childModel.setCustomerId(c.getString("customerId"));
                child.add(childModel);
            }
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseEvents() {

        try {
            JSONArray events = null;
            JSONObject jsonObject = new JSONObject(data);
            events = jsonObject.getJSONArray("events");

            event.clear();

            for (int i = 0; i < events.length(); i++) {
                JSONObject c = events.getJSONObject(i);
                EventsModel eventsModel = new EventsModel();
                eventsModel.setEventsId(c.getString("eventId"));
                eventsModel.setEventsName(c.getString("eventName"));
                eventsModel.setEventDate(c.getString("eventDate"));
                event.add(eventsModel);
            }
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseParentChild() {

        try {
            JSONArray children = null;
            JSONObject jsonObject = new JSONObject(data2);
            children = jsonObject.getJSONArray("child");

            child.clear();

            for (int i = 0; i < children.length(); i++) {
                JSONObject c = children.getJSONObject(i);
                ChildModel childModel = new ChildModel();
                childModel.setId(c.getString("childId"));
                childModel.setLastName(c.getString("lastName"));
                childModel.setFirstName(c.getString("firstName"));
                childModel.setMiddleName(c.getString("middleName"));
                childModel.setDob(c.getString("dob"));
                child.add(childModel);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseChildById() {

        try {
            JSONArray children = null;
            JSONObject jsonObject = new JSONObject(data2);
            children = jsonObject.getJSONArray("child");

            child.clear();

            for (int i = 0; i < children.length(); i++) {
                JSONObject c = children.getJSONObject(i);
                ChildModel childModel = new ChildModel();
                childModel.setId(c.getString("childId"));
                childModel.setLastName(c.getString("lastName"));
                childModel.setFirstName(c.getString("firstName"));
                childModel.setMiddleName(c.getString("middleName"));
                childModel.setDob(c.getString("dob"));
                childModel.setCustomerId(c.getString("customerId"));
                child.add(childModel);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseChildSessions() {

        try {
            JSONArray childSession;
            JSONObject jsonObject = new JSONObject(data3);
            childSession = jsonObject.getJSONArray("child_sessions");

            mChildSessionModels.clear();

            for (int i = 0; i < childSession.length(); i++) {
                JSONObject c = childSession.getJSONObject(i);
                ChildSessionModel childSessionModel = new ChildSessionModel();
                childSessionModel.setDate(c.getString("date"));
                childSessionModel.setTimeIn(c.getString("timeIn"));
                childSessionModel.setTimeOut(c.getString("timeOut"));
                childSessionModel.setDuration(c.getString("duration"));
                childSessionModel.setChildId(c.getString("childId"));
                childSessionModel.setSessionCost(c.getString("sessionCost"));
                mChildSessionModels.add(childSessionModel);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
