package com.jpappdesigns.nhishandz;

/**
 * Created by jonathan.perez on 8/4/16.
 */

public class BillsBackgroundWorker {

    /*private static final String TAG = GetSingleCustomerBackgroundWorker.class.getSimpleName();
    private Context mContext;
    private String mUrlAddress;
    private String mChildOfCustomer;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private String mCustomerId;
    private String mSendingFragment;
    private String mChildId;
    private Calendar mBeginningDate;
    private Calendar mEndDate;

    public BillsBackgroundWorker(Context context, String urlAddress, String childId, Calendar beginningDate, Calendar endDate) {
        mContext = context;
        mUrlAddress = urlAddress;
        mChildId = childId;
        mBeginningDate = beginningDate;
        mEndDate = endDate;
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
    protected Family doInBackground(String... params) {

        String customerData;
        String childOfCustomerData;
        String childSessionsData;

        if (mSendingFragment.equals("CustomerDetailFragment")) {
            customerData = this.downloadSingleCustomerData(mCustomerId);

            Family f = new Family();
            f.customerData = customerData;

            return f;
        } else {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String[] days = new String[5];
            int delta = mBeginningDate.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
            mBeginningDate.add(Calendar.DAY_OF_MONTH, delta);
            for (int i = 0; i < 5; i++) {
                days[i] = format.format(mBeginningDate.getTime());
                mBeginningDate.add(Calendar.DAY_OF_MONTH, 1);
            }

            customerData = this.downloadSingleCustomerData(mCustomerId);
            childSessionsData = this.childSessions(mChildId, days[0], days[4]);

            Family f = new Family();
            f.customerData = customerData;
            f.childSessionsData = childSessionsData;

            return f;
        }


    }

    @Override
    protected void onPostExecute(Family f) {
        super.onPostExecute(f);

        mProgressDialog.dismiss();

        if (mSendingFragment.equals("ChildDetailFragment")) {
            Parser customerParser = new Parser(mContext, f.customerData, f.childOfCustomerData, f.childSessionsData, mRecyclerView, "Child Details");
            customerParser.execute();
        } else if (mSendingFragment.equals("CustomerDetailFragment")) {
            if (f.customerData != null) {
                Parser customerParser = new Parser(mContext, f.customerData, f.childOfCustomerData, mRecyclerView, "Single Customer");
                customerParser.execute();
            }
        }

    }

    private String downloadSingleCustomerData(String customerId) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_SINGLE_CUSTOMER;
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

    public class Family {

        public String customerData;
        public String childOfCustomerData;
        public String childSessionsData;
    }

    private String childSessions(String childId, String date1, String date5) {

        InputStream inputStream = null;
        String line = null;

        try {
            String login_url = Constants.RETRIEVE_CHILD_SESSION_FOR_WEEK;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("childId", "UTF-8") + "=" + URLEncoder.encode(childId, "UTF-8") + "&" +
                    URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date1, "UTF-8") + "&" +
                    URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date5, "UTF-8");
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
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
