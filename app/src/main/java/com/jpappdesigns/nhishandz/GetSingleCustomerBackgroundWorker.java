package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
 * Created by jonathan.perez on 7/25/16.
 */
public class GetSingleCustomerBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = GetSingleCustomerBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress = Constants.RETRIEVE_SINGLE_CUSTOMER;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private String mCustomerId;

    public GetSingleCustomerBackgroundWorker(Context context, String urlAddress, RecyclerView recyclerView, String customerId) {
        mContext = context;
        mUrlAddress = urlAddress;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;

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
    protected String doInBackground(String... params) {

        String data = null;


        data = this.downloadSingleCustomerData(mCustomerId);
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        mProgressDialog.dismiss();;

        if (data != null) {
            Parser parser = new Parser(mContext, data, mRecyclerView, "Single Customer");
            parser.execute();
        }
    }

    private String downloadSingleCustomerData(String customerId) {

        InputStream inputStream = null;
        String line = null;

        try {

            Log.d(TAG, "downloadSingleCustomerData: " + mUrlAddress);
            URL url = new URL(mUrlAddress+"?"+customerId);
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
