package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by jonathan.perez on 8/16/16.
 */
public class UpdateChildSessionBackgroundWorker extends AsyncTask<String, Void, String> {

    private static final String TAG = UpdateChildSessionBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String mUrlAddress;
    private String mChildId;
    private String mDate;
    private String mTimeIn;
    private String mTimeOut;

    public UpdateChildSessionBackgroundWorker(Context context, String urlAddress, String date, String timeIn, String timeOut, String childId) {
        mContext = context;
        mUrlAddress = urlAddress;
        mDate = date;
        mTimeIn = timeIn;
        mTimeOut = timeOut;
        mChildId = childId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Updating session in database");
        mProgressDialog.setMessage("Updating...Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String data = null;

        data = this.updateData(mDate, mTimeIn, mTimeOut, mChildId);
        Log.d(TAG, "doInBackground: " + data);

        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        mProgressDialog.dismiss();

    }

    private String updateData(String date, String timeIn, String timeOut, String childId) {

        Log.d(TAG, "updateData: " + date);
        Log.d(TAG, "updateData: " + timeIn);
        Log.d(TAG, "updateData: " + timeOut );
        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.UPDATE_SESSION;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                    URLEncoder.encode("timeIn", "UTF-8") + "=" + URLEncoder.encode(timeIn, "UTF-8") + "&" +
                    URLEncoder.encode("timeOut", "UTF-8") + "=" + URLEncoder.encode(timeOut, "UTF-8") + "&" +
                    URLEncoder.encode("childId", "UTF-8") + "=" + URLEncoder.encode(childId, "UTF-8");
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
