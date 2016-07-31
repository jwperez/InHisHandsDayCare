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

/**
 * Created by jonathan.perez on 7/25/16.
 */
public class GetSingleCustomerBackgroundWorker extends AsyncTask<String, Void, GetSingleCustomerBackgroundWorker.Family> {

    private static final String TAG = GetSingleCustomerBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress = Constants.RETRIEVE_SINGLE_CUSTOMER;
    private String mChildOfCustomer = Constants.RETRIVE_CHILD_OF_PARENT;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private String mCustomerId;
    private String mSendingFragment;
    private String mChildId;

    public GetSingleCustomerBackgroundWorker(Context context, String urlAddress, String childOfCustomer, RecyclerView recyclerView, String customerId, String sendingFragment) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildOfCustomer = childOfCustomer;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;
        mSendingFragment = sendingFragment;
    }

    public GetSingleCustomerBackgroundWorker(Context context, String urlAddress, String childOfCustomer, RecyclerView recyclerView, String customerId, String childId, String sendingFragment) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildOfCustomer = childOfCustomer;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;
        mChildId = childId;
        mSendingFragment = sendingFragment;
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

        Log.d(TAG, "doInBackground: " + params[0]);

        if (mSendingFragment.equals("CustomerDetailFragment")) {
            customerData = this.downloadSingleCustomerData(mCustomerId);
            childOfCustomerData = this.downloadChildOfSingleCustomerData(mCustomerId);

            Family f = new Family();
            f.customerData = customerData;
            f.childofCustomerData = childOfCustomerData;
            Log.d(TAG, "doInBackground: " + f.customerData);
            Log.d(TAG, "doInBackground: " + f.childofCustomerData);

            return f;
        } else {
            customerData = this.downloadSingleCustomerData(mCustomerId);
            childOfCustomerData = this.downloadChildById(mChildId);

            Family f = new Family();
            f.customerData = customerData;
            f.childofCustomerData = childOfCustomerData;
            Log.d(TAG, "doInBackground: " + f.customerData);
            Log.d(TAG, "doInBackground: " + f.childofCustomerData);

            return f;
        }


    }

    @Override
    protected void onPostExecute(Family f) {
        super.onPostExecute(f);

        mProgressDialog.dismiss();

        Log.d(TAG, "onPostExecute: " + mSendingFragment);

        if (mSendingFragment.equals("ChildDetailFragment")) {
            Parser customerParser = new Parser(mContext, f.customerData, f.childofCustomerData, mRecyclerView, "Child Details");
            customerParser.execute();
        } else if (mSendingFragment.equals("CustomerDetailFragment")) {
            if (f.customerData != null) {
                Parser customerParser = new Parser(mContext, f.customerData, f.childofCustomerData, mRecyclerView, "Single Customer");
                customerParser.execute();
            }
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
            String login_url = Constants.RETRIVE_CHILD_OF_PARENT;
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

    public class Family {

        public String customerData;
        public String childofCustomerData;
    }
}
