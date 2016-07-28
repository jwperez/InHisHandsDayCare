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
 * Created by jonathan.perez on 7/26/16.
 */
public class GetChildOfParentBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = GetSingleCustomerBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress = Constants.RETRIEVE_SINGLE_CUSTOMER;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private String mCustomerId;

    public GetChildOfParentBackgroundWorker(Context context, String urlAddress, RecyclerView recyclerView, String customerId) {
        mContext = context;
        mUrlAddress = urlAddress;
        mRecyclerView = recyclerView;
        mCustomerId = customerId;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /*mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Downloading Data");
        mProgressDialog.setMessage("Downloading...Please wait.");
        mProgressDialog.show();*/
    }


    @Override
    protected String doInBackground(String... params) {

        String data = null;

        Log.d(TAG, "doInBackground: " + params[0]);

        data = this.downloadSingleCustomerData(mCustomerId);

        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        //mProgressDialog.dismiss();;

        if (data != null) {
            Parser parser = new Parser(mContext, data, mRecyclerView, "Child Of Parent");
            parser.execute();
        }
    }

    private String downloadSingleCustomerData(String customerId) {

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
}
