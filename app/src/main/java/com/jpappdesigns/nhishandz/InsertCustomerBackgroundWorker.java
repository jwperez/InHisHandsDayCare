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
 * Created by jonathan.perez on 8/21/16.
 */
public class InsertCustomerBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = InsertCustomerBackgroundWorker.class.getSimpleName();
    private AsyncStringResult mCallback;
    private Context mContext;
    private String mUrlAddress;
    private ProgressDialog mProgressDialog;
    private String mLastName;
    private String mFirstName;
    private String mMiddleName;
    private String mEmail;
    private String mPhoneNumber;
    private String mRelationshipToChild;

    public InsertCustomerBackgroundWorker(Context context, String urlAddress, String lastName, String firstName,
                                          String middleName, String email, String phoneNumber,
                                          String relationshipToChild, AsyncStringResult callback) {
        mContext = context;
        mUrlAddress = urlAddress;
        mLastName = lastName;
        mFirstName = firstName;
        mMiddleName = middleName;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mRelationshipToChild = relationshipToChild;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Adding Customer to Database");
        mProgressDialog.setMessage("Adding Customer to Database... Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String data = null;

        data = this.insertData(mLastName, mFirstName, mMiddleName, mEmail, mPhoneNumber, mRelationshipToChild);

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mCallback.onResult(result);
        mProgressDialog.dismiss();
    }

    private String insertData(String lastName, String firstName, String middleName, String email,
                              String phoneNumber, String relationshipToChild) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.INSERT_CUSTOMER;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&" +
                    URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                    URLEncoder.encode("middleName", "UTF-8") + "=" + URLEncoder.encode(middleName, "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                    URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                    URLEncoder.encode("relationshipToChild", "UTF-8") + "=" + URLEncoder.encode(relationshipToChild, "UTF-8");
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
            Log.d(TAG, "insertData: " + result);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
