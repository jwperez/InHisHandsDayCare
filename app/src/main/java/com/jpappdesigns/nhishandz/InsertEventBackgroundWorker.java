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
public class InsertEventBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = InsertEventBackgroundWorker.class.getSimpleName();
    private AsyncStringResult mCallback;
    private Context mContext;
    private String mUrlAddress;
    private ProgressDialog mProgressDialog;
    private String mEventName;
    private String mEventDate;

    public InsertEventBackgroundWorker(Context context, String urlAddress, String eventName, String eventDate, AsyncStringResult callback) {
        mContext = context;
        mUrlAddress = urlAddress;
        mEventName = eventName;
        mEventDate = eventDate;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Adding Event to Database");
        mProgressDialog.setMessage("Adding Event to Database... Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String data = null;

        data = this.insertData(mEventName, mEventDate);

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mCallback.onResult(result);
        mProgressDialog.dismiss();
    }

    private String insertData(String eventName, String eventDate) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.INSERT_EVENT;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("eventName", "UTF-8") + "=" + URLEncoder.encode(eventName, "UTF-8") + "&" +
                    URLEncoder.encode("eventDate", "UTF-8") + "=" + URLEncoder.encode(eventDate, "UTF-8");
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
