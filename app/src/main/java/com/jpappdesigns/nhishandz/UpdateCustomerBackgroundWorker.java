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
 * Created by jonathan.perez on 8/22/16.
 */
public class UpdateCustomerBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = UpdateCustomerBackgroundWorker.class.getSimpleName();
    private AsyncStringResult mCallback;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String mCustomerId;
    private String mCustomerLastName;
    private String mCustomerFirstName;
    private String mCustomerMiddleName;
    private String mCustomerEmail;
    private String mCustomerPhoneNumber;
    private String mCustomerRelationship;

    public UpdateCustomerBackgroundWorker(Context context, String customerId, String customerLastName,
                                          String customerFirstName, String customerMiddleName, String customerEmail,
                                          String customerPhoneNumber, String customerRelationship,
                                          AsyncStringResult callback) {
        mContext = context;
        mCustomerId = customerId;
        mCustomerLastName = customerLastName;
        mCustomerFirstName = customerFirstName;
        mCustomerMiddleName = customerMiddleName;
        mCustomerEmail = customerEmail;
        mCustomerPhoneNumber = customerPhoneNumber;
        mCustomerRelationship = customerRelationship;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Updating customer in database");
        mProgressDialog.setMessage("Updating...Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String data = null;

        data = this.updateData(mCustomerId, mCustomerLastName, mCustomerFirstName, mCustomerMiddleName, mCustomerEmail,
                mCustomerPhoneNumber, mCustomerRelationship);
        Log.d(TAG, "doInBackground: " + data);

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mCallback.onResult(result);
        mProgressDialog.dismiss();
    }

    private String updateData(String customerId, String customerLastName, String customerFirstName, String customerMiddleName, String customerEmail, String customerPhoneNumber, String customerRelationship) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.UPDATE_CUSTOMER;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("customerId", "UTF-8") + "=" + URLEncoder.encode(customerId, "UTF-8") + "&" +
                    URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(customerLastName, "UTF-8") + "&" +
                    URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(customerFirstName, "UTF-8") + "&" +
                    URLEncoder.encode("middleName", "UTF-8") + "=" + URLEncoder.encode(customerMiddleName, "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(customerEmail, "UTF-8") + "&" +
                    URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(customerPhoneNumber, "UTF-8") + "&" +
                    URLEncoder.encode("relationshipToChild", "UTF-8") + "=" + URLEncoder.encode(customerRelationship, "UTF-8");
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
