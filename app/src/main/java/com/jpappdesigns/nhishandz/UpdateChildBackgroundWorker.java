package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jpappdesigns.nhishandz.utils.AsyncStringResult;

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
 * Created by jonathan.perez on 8/23/16.
 */
public class UpdateChildBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = UpdateCustomerBackgroundWorker.class.getSimpleName();
    private AsyncStringResult mCallback;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String mChildId;
    private String mChildLastName;
    private String mChildFirstName;
    private String mChildMiddleName;
    private String mChildDob;
    private String mCustomerId;

    public UpdateChildBackgroundWorker(Context context, String childId, String childLastName,
                                       String childFirstName, String childMiddleName,
                                       String childDob, String customerId,
                                       AsyncStringResult callback) {
        mContext = context;
        mChildId = childId;
        mChildLastName = childLastName;
        mChildFirstName = childFirstName;
        mChildMiddleName = childMiddleName;
        mChildDob = childDob;
        mCustomerId = customerId;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Updating child in database");
        mProgressDialog.setMessage("Updating...Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String data = null;

        data = this.updateData(mChildId, mChildLastName, mChildFirstName, mChildMiddleName, mChildDob, mCustomerId);
        Log.d(TAG, "doInBackground: " + data);

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mCallback.onResult(result);
        mProgressDialog.dismiss();
    }

    private String updateData(String childId, String childLastName, String childFirstName, String childMiddleName, String childDob, String customerId) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.UPDATE_CHILD;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("childId", "UTF-8") + "=" + URLEncoder.encode(childId, "UTF-8") + "&" +
                    URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(childLastName, "UTF-8") + "&" +
                    URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(childFirstName, "UTF-8") + "&" +
                    URLEncoder.encode("middleName", "UTF-8") + "=" + URLEncoder.encode(childMiddleName, "UTF-8") + "&" +
                    URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(childDob, "UTF-8") + "&" +
                    URLEncoder.encode("customerId", "UTF-8") + "=" + URLEncoder.encode(customerId, "UTF-8");
            bufferedWriter.write(post_data);
            Log.d(TAG, "insertData: " + post_data);
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
