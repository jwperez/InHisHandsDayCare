package com.jpappdesigns.nhishandz;

public class Constants {

    // Hourly cost
    public static final double HOURLY_COST = 3.75;

    public static final String IP_ADDRESS = "192.168.2.3";

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String LOGIN_OPERATION = "login";
    public static final String REQUEST_METHOD = "POST";
    public static final String SUCCESS = "Successful";

    public static final String RETRIEVE_SINGLE_CUSTOMER = "http://" + IP_ADDRESS + "/nHisHandz/get_single_customer.php";
    public static final String RETRIEVE_CHILD_OF_PARENT = "http://" + IP_ADDRESS + "/nHisHandz/get_single_child.php";
    public static final String LOGIN_URL = "http://" + IP_ADDRESS + "/nHisHandz/login.php";
    public static final String GET_CUSTOMERS_URL = "http://" + IP_ADDRESS + "/nHisHandz/get_customers.php";
    public static final String GET_CHILDREN_URL = "http://" + IP_ADDRESS + "/nHisHandz/get_children.php";
    public static final String RETRIEVE_CHILD_BY_ID = "http://" + IP_ADDRESS + "/nHisHandz/get_child_by_id.php";
    public static final String RETRIEVE_CHILD_SESSION_FOR_WEEK = "http://" + IP_ADDRESS + "/nHisHandz/get_session_week.php";
    public static final String INSERT_CHILD_SESSION = "http://" + IP_ADDRESS + "/nHisHandz/insert_session.php";
    public static final String INSERT_CUSTOMER = "http://" + IP_ADDRESS + "/nHisHandz/insert_customer.php";
    public static final String INSERT_CHILD = "http://" + IP_ADDRESS + "/nHisHandz/insert_child.php";
    public static final String INSERT_EVENT = "http://" + IP_ADDRESS + "/nHisHandz/insert_event.php";
    public static final String RETRIEVE_SINGLE_SESSION = "http://" + IP_ADDRESS + "/nHisHandz/get_single_session.php";
    public static final String UPDATE_SESSION = "http://" + IP_ADDRESS + "/nHisHandz/update_session.php";
    public static final String UPDATE_CUSTOMER = "http://" + IP_ADDRESS + "/nHisHandz/update_customer.php";
    public static final String UPDATE_CHILD = "http://" + IP_ADDRESS + "/nHisHandz/update_child.php";
    public static final String GET_EVENTS = "http://" + IP_ADDRESS + "/nHisHandz/get_events.php";

    public static final int CHILD_INFO = 0;
    public static final int CHILD_SESSIONS = 1;

    // Customer Details
    public static final int CUSTOMER_INFO = 0;
    public static final int CHILD_OF_PARENT_INFO = 1;
    public static final int PARENT_GUARDIAN_OF_CHILD = 2;
    public static final int BILL_TOTAL = 3;

}
