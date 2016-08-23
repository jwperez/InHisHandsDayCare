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
 * Created by jonathan.perez on 8/17/16.
 */
public class GetEventsBackgroundWorker extends AsyncTask<Void, Integer, String> {

    private static final String TAG = GetEventsBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private String mUrlAddress;
    private ProgressDialog mProgressDialog;

    public GetEventsBackgroundWorker(Context context, String urlAddress, RecyclerView recyclerView) {
        mContext = context;
        mUrlAddress = urlAddress;
        mRecyclerView = recyclerView;
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
    protected String doInBackground(Void... voids) {

        String data = null;

        data = this.downloadEvents();

        Log.d(TAG, "doInBackground: " + data);
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

        Parser parser = new Parser(mContext, data, mRecyclerView, "Events");
        parser.execute();
    }

    private String downloadEvents() {

        InputStream inputStream = null;
        String line = null;

        try {
            mUrlAddress = Constants.GET_EVENTS;
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
