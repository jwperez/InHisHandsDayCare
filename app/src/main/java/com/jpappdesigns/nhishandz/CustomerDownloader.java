package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Spinner;

import com.jpappdesigns.nhishandz.utils.Parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jonathan.perez on 7/20/16.
 */
public class CustomerDownloader extends AsyncTask<Void, Integer, String> {

    private static final String TAG = CustomerDownloader.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private Spinner mChildrenSpinner;
    private Spinner mCustomerSpinner;
    private String mFragmentName;

    public CustomerDownloader(Context context, String urlAddress, RecyclerView recyclerView, String fragmentName) {
        mContext = context;
        mUrlAddress = urlAddress;
        mRecyclerView = recyclerView;
        mFragmentName = fragmentName;
    }

    public CustomerDownloader(Context context, String urlAddress, Spinner spinner, String fragmentName) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildrenSpinner = spinner;
        mFragmentName = fragmentName;
    }

    public CustomerDownloader(Context context, String urlAddress, Spinner spinner) {
        mContext = context;
        mUrlAddress = urlAddress;
        mCustomerSpinner = spinner;
        mFragmentName = "AddChildFragment";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Downloading Data");
        mProgressDialog.setMessage("Downloading...Please wait.");
        //mProgressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        String data = null;

        Log.d(TAG, "doInBackground: " + mFragmentName);

        if (mFragmentName.equals("CustomerListFragment")) {
            data = this.downloadCustomers();
        } else if (mFragmentName.equals("ChildListFragment") || mFragmentName.equals("RecordSessionFragment")
                || mFragmentName.equals("UpdateSessionFragment") || mFragmentName.equals("MonthlyReportsFragment")) {
            data = this.downloadChildren();
        } else {
            data = this.downloadCustomers();
        }

        return data;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        mProgressDialog.dismiss();

        //Log.d(TAG, "onPostExecute: " + mFragmentName);

        //Log.d(TAG, "onPostExecute: " + data);
        if (data != null && mFragmentName.equals("CustomerListFragment")) {
            Parser parser = new Parser(mContext, data, mRecyclerView, "All Customers");
            parser.execute();
        } else if (mFragmentName.equals("ChildListFragment")) {
            //Log.d(TAG, "onPostExecute: inside ChildListFragment" + data);
            Parser parser = new Parser(mContext, data, mRecyclerView, "All Children");
            parser.execute();
        } else if (mFragmentName.equals("MonthlyReportsFragment")) {
            Parser parser = new Parser(mContext, data, mChildrenSpinner, "MonthlyReportsFragment");
            parser.execute();
        } else if (mFragmentName.equals("RecordSessionFragment")) {
            Parser parser = new Parser(mContext, data, mChildrenSpinner, "RecordSessionFragment");
            parser.execute();
        } else if (mFragmentName.contains("UpdateSessionFragment")) {
            Parser parser = new Parser(mContext, data, mChildrenSpinner, "UpdateSessionFragment");
            parser.execute();
        } else {
            Parser parser = new Parser(mContext, data, mCustomerSpinner, "AddChildFragment", "extra");
            parser.execute();
        }


    }

    private String downloadCustomers() {

        InputStream inputStream = null;
        String line = null;

        try {
            mUrlAddress = Constants.GET_CUSTOMERS_URL;
            URL url = new URL(mUrlAddress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
            } else {
                return null;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String downloadChildren() {

        InputStream inputStream = null;
        String line = null;

        try {
            mUrlAddress = Constants.GET_CHILDREN_URL;
            URL url = new URL(mUrlAddress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
            } else {
                return null;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
