package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
public class Downloader extends AsyncTask<Void, Integer, String> {

    private Context mContext;
     private String mUrlAddress = Constants.GET_CUSTOMERS_URL;
    private ProgressDialog mProgressDialog;

    public Downloader(Context context) {
        this.mContext = context;
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

        String data = this.downloadCustomers();
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

        if (data != null) {

        } else {
            Toast.makeText(mContext, "Unable to download customer data", Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadCustomers() {

        InputStream inputStream = null;
        String line = null;

        try {
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
