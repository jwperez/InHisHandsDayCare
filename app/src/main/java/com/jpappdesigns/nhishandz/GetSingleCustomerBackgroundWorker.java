package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jpappdesigns.nhishandz.utils.Parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jonathan.perez on 7/25/16.
 */
public class GetSingleCustomerBackgroundWorker extends AsyncTask<String, Void, GetSingleCustomerBackgroundWorker.Family> {

    private static final String TAG = GetSingleCustomerBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress = Constants.RETRIEVE_SINGLE_CUSTOMER;
    private String mChildOfCustomer = Constants.RETRIEVE_CHILD_OF_PARENT;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private String mCustomerId;
    private String mSendingFragment;
    private String mChildId;
    private Calendar mCurrentDate;
    private String mStartDate;
    private String mEndDate;


    public GetSingleCustomerBackgroundWorker(Context context, String urlAddress, String childOfCustomer, RecyclerView recyclerView, String customerId, String sendingFragment) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildOfCustomer = childOfCustomer;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;
        mSendingFragment = sendingFragment;
    }

    public GetSingleCustomerBackgroundWorker(Context context, String urlAddress, String childOfCustomer, RecyclerView recyclerView, String customerId, String childId, String sendingFragment, Calendar currentDate) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildOfCustomer = childOfCustomer;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;
        mChildId = childId;
        mSendingFragment = sendingFragment;
        mCurrentDate = currentDate;
    }

    public GetSingleCustomerBackgroundWorker(Context context, String urlAddress, String childOfCustomer, RecyclerView recyclerView, String customerId, String childId, String sendingFragment, String startDate, String endDate) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildOfCustomer = childOfCustomer;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;
        Log.d(TAG, "GetSingleCustomerBackgroundWorker: " + mCustomerId);
        mChildId = childId;
        mSendingFragment = sendingFragment;
        mStartDate = startDate;
        mEndDate = endDate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Downloading Data");
        mProgressDialog.setMessage("Downloading...Please wait.");
        mProgressDialog.show();
    }


    @Override
    protected Family doInBackground(String... params) {

        String customerData;
        String childOfCustomerData;
        String childSessionsData;


        if (mSendingFragment.equals("CustomerDetailFragment")) {
            customerData = this.downloadSingleCustomerData(mCustomerId);
            childOfCustomerData = this.downloadChildOfSingleCustomerData(mCustomerId);

            Family f = new Family();
            f.customerData = customerData;
            f.childOfCustomerData = childOfCustomerData;

            return f;
        } else if (mSendingFragment.equals("MonthlyReportsPrintoutFragment")) {

            //mCustomerId = params[0];
            //mChildId = params[1];

            Log.d(TAG, "doInBackground: CustomerId" + mCustomerId);
            customerData = this.downloadSingleCustomerData(mCustomerId);
            childOfCustomerData = this.downloadChildById(mChildId);

            Log.d(TAG, "doInBackground: " + mStartDate);
            Log.d(TAG, "doInBackground: " + mEndDate);
            childSessionsData = this.childSessions(mChildId, mStartDate, mEndDate);

            Family f = new Family();
            f.customerData = customerData;
            f.childOfCustomerData = childOfCustomerData;
            f.childSessionsData = childSessionsData;

            return f;

        } else {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Log.d(TAG, "doInBackground: current" + format.format(mCurrentDate.getTime()));
            String[] days = new String[5];
            int delta = -mCurrentDate.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
            mCurrentDate.add(Calendar.DAY_OF_MONTH, delta);
            for (int i = 0; i < 5; i++) {

                days[i] = format.format(mCurrentDate.getTime());
                mCurrentDate.add(Calendar.DAY_OF_MONTH, 1);
            }

            Log.d(TAG, "doInBackground: " + days[0]);
            Log.d(TAG, "doInBackground: " + days[4]);
            customerData = this.downloadSingleCustomerData(mCustomerId);
            childOfCustomerData = this.downloadChildById(mChildId);
            childSessionsData = this.childSessions(mChildId, days[0], days[4]);

            Family f = new Family();
            f.customerData = customerData;
            f.childOfCustomerData = childOfCustomerData;
            f.childSessionsData = childSessionsData;

            return f;
        }
    }

    @Override
    protected void onPostExecute(Family f) {
        super.onPostExecute(f);

        mProgressDialog.dismiss();

        if (mSendingFragment.equals("ChildDetailFragment")) {
            Parser customerParser = new Parser(mContext, f.customerData, f.childOfCustomerData, f.childSessionsData, mRecyclerView, "Child Details");
            Log.d(TAG, "onPostExecute: " + f.childSessionsData);
            customerParser.execute();
        } else if (mSendingFragment.equals("CustomerDetailFragment")) {
            if (f.customerData != null) {
                Parser customerParser = new Parser(mContext, f.customerData, f.childOfCustomerData, mRecyclerView, "Single Customer");
                customerParser.execute();
            }
        } else if (mSendingFragment.equals("MonthlyReportsPrintoutFragment")) {
            Parser customerParser = new Parser(mContext, f.customerData, f.childOfCustomerData, f.childSessionsData, mRecyclerView, "MonthlyReportsPrintoutFragment");
            Log.d(TAG, "onPostExecute: " + f.childSessionsData);
            customerParser.execute();
        }
    }

    private String downloadSingleCustomerData(String customerId) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_SINGLE_CUSTOMER;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("customerId", "UTF-8") + "=" + URLEncoder.encode(customerId, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String downloadChildOfSingleCustomerData(String customerId) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_CHILD_OF_PARENT;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("customerId", "UTF-8") + "=" + URLEncoder.encode(customerId, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String downloadChildById(String childId) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_CHILD_BY_ID;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("childId", "UTF-8") + "=" + URLEncoder.encode(childId, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String childSessions(String childId, String date1, String date5) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_CHILD_SESSION_FOR_WEEK;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(Constants.REQUEST_METHOD);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("childId", "UTF-8") + "=" + URLEncoder.encode(childId, "UTF-8") + "&"+
                    URLEncoder.encode("date1", "UTF-8") + "=" + URLEncoder.encode(date1, "UTF-8") + "&" +
                    URLEncoder.encode("date5", "UTF-8") + "=" + URLEncoder.encode(date5, "UTF-8");
            bufferedWriter.write(post_data);

            Log.d(TAG, "childSessions: " + post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));


            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            //result += System.getProperty("line.separator") + responseOutput.toString();

            inputStream.close();
            httpURLConnection.disconnect();
            Log.d(TAG, "childSessions: " + result.toString());
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Family {

        public String customerData;
        public String childOfCustomerData;
        public String childSessionsData;
    }
}
