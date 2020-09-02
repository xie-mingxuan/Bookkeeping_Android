package com.example.bookkeeping.others;

public class Constant {
    private static final String IP = "http://192.168.68.91:8080";
    private static final String USER_API = "/api/user";
    private static final String ADMIN_API = "/api/admin";
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String MANAGE_MONEY = "/manageMoney";
    private static final String DELETE = "/deleteAccount";
    private static final String CHANGE_PASSWORD = "/changePassword";
    private static final String QUERY_RECORDS = "/queryRecords";
    private static final String ADD_USER = "/addUser";
    private static final String ADD_ADMIN = "/addAdmin";

    // USER_API
    public static final String API_USER_LOGIN = IP + USER_API + LOGIN;
    public static final String API_USER_REGISTER = IP + USER_API + REGISTER;
    public static final String API_USER_MANAGE_MONEY = IP + USER_API + MANAGE_MONEY;
    public static final String API_USER_DELETE_ACCOUNT = IP + USER_API + DELETE;
    public static final String API_USER_CHANGE_PASSWORD = IP + USER_API + CHANGE_PASSWORD;
    public static final String API_USER_QUERY_RECORDS = IP + USER_API + QUERY_RECORDS;

    //ADMIN_API
    public static final String API_ADMIN_LOGIN = IP + ADMIN_API + LOGIN;
    public static final String API_ADMIN_MANAGE_MONEY = IP + ADMIN_API + MANAGE_MONEY;
    public static final String API_ADMIN_ADD_USER = IP + ADMIN_API + ADD_USER;
    public static final String API_ADMIN_ADD_ADMIN = IP + ADMIN_API + ADD_ADMIN;
    public static final String API_ADMIN_DELETE_ACCOUNT = IP + ADMIN_API + DELETE;
    public static final String API_ADMIN_CHANGE_PASSWORD = IP + ADMIN_API + CHANGE_PASSWORD;
    public static final String API_ADMIN_QUERY_RECORDS = IP + ADMIN_API + QUERY_RECORDS;

    //ADMIN_OPTION
    public static final int OPTION_ADD_USER = 1;
    public static final int OPTION_ADD_ADMIN = 2;
    public static final int OPTION_DELETE = 3;
    public static final int OPTION_CHANGE_MONEY = 4;

    //USER_TYPE
    public static final int USER = 1;
    public static final int ADMIN = 2;

    //REGISTER_STATUES
    public static final int SUCCESS = 0;
    public static final int USERNAME_OCCUPIED = 1;
    public static final int NETWORK_ERROR = 2;
}
