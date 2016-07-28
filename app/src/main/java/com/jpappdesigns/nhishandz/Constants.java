package com.jpappdesigns.nhishandz;

public class Constants {

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String LOGIN_OPERATION = "login";
    public static final String RETRIEVE_SINGLE_CUSTOMER = "http://172.31.98.115/nHisHandz/get_single_customer.php";
    public static final String RETRIVE_CHILD_OF_PARENT = "http://172.31.98.115/nHisHandz/get_single_child.php";
    //public static final String LOGIN_URL = "http://192.168.2.7:8888/in_his_hands/login.php";
    public static final String LOGIN_URL = "http://192.168.2.6/nHisHandz/login.php";
    public static final String REQUEST_METHOD = "POST";
    public static final String SUCCESS = "Successful";
    public static final String GET_CUSTOMERS_URL = "http://172.31.98.115/nHisHandz/get_customers.php";
    public static final String GET_CHILDREN_URL = "http://172.31.98.115/nHisHandz/get_children.php";

    public static final int CHILD_INFO = 0;
    public static final int CHILD_SESSIONS = 1;


    // CUstomer Details
    public static final int CUSTOMER_INFO = 0;
    public static final int CHILD_OF_PARENT_INFO = 1;
    public static final int PARENT_GUARDIAN_OF_CHILD = 2;
}
