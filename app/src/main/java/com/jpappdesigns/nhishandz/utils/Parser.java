package com.jpappdesigns.nhishandz.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jpappdesigns.nhishandz.model.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jonathan.perez on 7/20/16.
 */
public class Parser extends AsyncTask<Void, Integer, Integer> {

    private Context mContext;
    private String data;
    private ProgressDialog mProgressDialog;
    private ArrayList<CustomerModel> customers = new ArrayList<>();

    public Parser(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Parsing Data");
        mProgressDialog.setMessage("Parsing....Please wait.");
        mProgressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        mProgressDialog.dismiss();

        if (integer == 1) {

        } else {
            Toast.makeText(mContext, "Unable to parse customer data.", Toast.LENGTH_LONG).show();
        }
    }

    private int parse() {

        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject = null;

            customers.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                CustomerModel customerModel = new CustomerModel();
                customerModel.setLastName(jsonObject.getString("lastName"));
                customerModel.setLastName(jsonObject.getString("lastName"));
                customerModel.setLastName(jsonObject.getString("lastName"));
                customerModel.setLastName(jsonObject.getString("lastName"));
                customerModel.setLastName(jsonObject.getString("lastName"));
                customerModel.setLastName(jsonObject.getString("lastName"));
                customerModel.setLastName(jsonObject.getString("lastName"));
                customers.add(customerModel);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
