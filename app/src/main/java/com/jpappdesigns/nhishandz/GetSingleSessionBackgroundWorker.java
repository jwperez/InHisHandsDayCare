package com.jpappdesigns.nhishandz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jpappdesigns.nhishandz.model.ChildSessionModel;
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
import java.util.ArrayList;

/**
 * Created by jonathan.perez on 8/16/16.
 */
public class GetSingleSessionBackgroundWorker extends AsyncTask<Void, Integer, String> {

    private static final String TAG = GetSingleCustomerBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress = Constants.RETRIEVE_SINGLE_CUSTOMER;
    private String mChildOfCustomer = Constants.RETRIEVE_CHILD_OF_PARENT;
    private ArrayList<ChildSessionModel> mChildSessionModels= new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private String mSessionDate;
    private String mChildId;
    AsyncStringResult mcallback;


    public GetSingleSessionBackgroundWorker(Context context, String urlAddress, String childId, String sessionDate,  AsyncStringResult callback) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildId = childId;
        mSessionDate = sessionDate;
        mcallback = callback;
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

        data = this.getSingleSessions(mChildId, mSessionDate);

        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        //super.onPostExecute(data);

        mProgressDialog.dismiss();

        //Parser singleSessionParser = new Parser(mContext, data, "Update Session");
        //singleSessionParser.execute();

        mcallback.onResult(data);
    }

    private String getSingleSessions(String childId, String date1) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_SINGLE_SESSION;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(Constants.REQUEST_METHOD);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("childId", "UTF-8") + "=" + URLEncoder.encode(childId, "UTF-8") + "&" +
                    URLEncoder.encode("date1", "UTF-8") + "=" + URLEncoder.encode(date1, "UTF-8");
            bufferedWriter.write(post_data);

            Log.d(TAG, "childSessions: " + post_data);
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
            //result += System.getProperty("line.separator") + responseOutput.toString();

            inputStream.close();
            httpURLConnection.disconnect();
            Log.d(TAG, "childSessions: " + result.toString());
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
