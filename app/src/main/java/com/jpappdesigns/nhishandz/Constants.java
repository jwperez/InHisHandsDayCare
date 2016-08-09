package com.jpappdesigns.nhishandz;

public class Constants {

    // Hourly cost
    public static final double HOURLY_COST = 3.75;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String LOGIN_OPERATION = "login";
    public static final String RETRIEVE_SINGLE_CUSTOMER = "http://10.63.112.199/nHisHandz/get_single_customer.php";
    public static final String RETRIVE_CHILD_OF_PARENT = "http://10.63.112.199/nHisHandz/get_single_child.php";
    //public static final String LOGIN_URL = "http://192.168.2.7:8888/in_his_hands/login.php";
    public static final String LOGIN_URL = "http://10.63.112.199/nHisHandz/login.php";
    public static final String REQUEST_METHOD = "POST";
    public static final String SUCCESS = "Successful";
    public static final String GET_CUSTOMERS_URL = "http://10.63.112.199/nHisHandz/get_customers.php";
    public static final String GET_CHILDREN_URL = "http://10.63.112.199/nHisHandz/get_children.php";

    public static final int CHILD_INFO = 0;
    public static final int CHILD_SESSIONS = 1;


    // CUstomer Details
    public static final int CUSTOMER_INFO = 0;
    public static final int CHILD_OF_PARENT_INFO = 1;
    public static final int PARENT_GUARDIAN_OF_CHILD = 2;
    public static final String RETRIEVE_CHILD_BY_ID = "http://10.63.112.199/nHisHandz/get_child_by_id.php";

    // Child Details
    public static final String RETRIEVE_CHILD_SESSION_FOR_WEEK = "http://10.63.112.199/nHisHandz/get_session_week.php";
}
