package com.jataaka.themoviefan;

import com.android.volley.Request;

/**
 * Created by nikola on 16/01/2016.
 */
public class GlobalConstants {
    public static final int HomeFragmentIndex = 0;
    public static final int FavoritesFragmentIndex = 1;
    public static final int WatchlistFragmentIndex = 2;
    public static final int LogoutFragmentIndex = 3;
    public static final int LoginFragmentIndex = 4;
    public static final int SettingsFragmentIndex = 5;
    public static final int AboutFragmentIndex = 6;

    public static final String ERRROR_RESPONSE_PROPERTY = "error";
    public static final String TOKEN_RESPONSE_PROPERTY = "sessionToken";
    public static final String USERNAME_REQUEST_PROPERTY = "username";
    public static final String PASSWORD_REQUEST_PROPERTY = "password";

    public static final String USER_REGISTERED_EN = "User registered!";
    public static final String USERNAME_REQUIRED_EN = "Email is required!";
    public static final String PASSWORD_REQUIRED_EN = "Password is required!";
    public static final String PASSWORDS_NOT_MATCH_EN = "Passwords does not match!";

    public static final String HEADER_APP_ID_NAME = "X-Parse-Application-Id";
    public static final String HEADER_APP_ID_VALUE = "7G5mP5LoSpeEkuxMc6UGRHFsgt64EFGvkCH29trM";

    public static final String HEADER_RESTAPI_KEY_NAME = "X-Parse-REST-API-Key";
    public static final String HEADER_RESTAPI_KEY_VALUE = "nVt0bLmNE237BCDMESxTEK2JHKnHprDeUEY6X96s";

    public static final String REGISTER_USER_ENDPOINT = "https://api.parse.com/1/users";
    public static final String REGISTER_USER_METHOD = "POST";

    public static final String LOGIN_USER_ENDPOINT = "https://api.parse.com/1/login?username=%s&password=%s";
    public static final int LOGIN_USER_METHOD = Request.Method.GET;
}
