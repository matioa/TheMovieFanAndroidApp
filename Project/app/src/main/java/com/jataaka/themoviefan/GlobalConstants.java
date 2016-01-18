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
    public static final int RegisterFragmentIndex = 7;
    public static final int SearchFragmentIndex = 8;

    public static final String USER_REGISTERED_EN = "User registered!";
    public static final String USERNAME_REQUIRED_EN = "Username is required!";
    public static final String PASSWORD_REQUIRED_EN = "Password is required!";
    public static final String PASSWORDS_NOT_MATCH_EN = "Passwords does not match!";
    public static final String USER_LOGGEDIN_EN = "User is loggedIn!";
    public static final String USER_LOGGEDOUT_EN = "You are logged out!";

    public static final String HEADER_APP_ID_NAME = "X-Parse-Application-Id";
    public static final String HEADER_APP_ID_VALUE = "7G5mP5LoSpeEkuxMc6UGRHFsgt64EFGvkCH29trM";

    public static final String HEADER_RESTAPI_KEY_NAME = "X-Parse-REST-API-Key";
    public static final String HEADER_RESTAPI_KEY_VALUE = "nVt0bLmNE237BCDMESxTEK2JHKnHprDeUEY6X96s";

    public static final String REGISTER_USER_ENDPOINT = "https://api.parse.com/1/users";
    public static final String REGISTER_USER_BODY = "{\"username\":\"%s\",\"password\":\"%s\",\"liked\":[],\"watchlist\":[],\"profilePicURL\":\"\",\"email\":\"%s\"}";
    public static final int REGISTER_USER_METHOD = Request.Method.POST;

    public static final String HEADER_TMDB_API_KEY_NAME = "api_key";
    public static final String HEADER_TMDB_API_KEY_VALUE = "9bcc7d5492fb90c523ac4607906011eb";

    public static final String LOGIN_USER_ENDPOINT = "https://api.parse.com/1/login?username=%s&password=%s";

    public static final String MOVIES_POPULAR_ENDPOINT = "http://api.themoviedb.org/3/movie/popular?%s=%s&page=%d";
    public static final String MOVIE_BYID_ENDPOINT = "http://api.themoviedb.org/3/movie/%d?%s=%s";

    public static final int LOGIN_USER_METHOD = Request.Method.GET;

    public static final String SAVED_USERNAME_KEY = "username";
    public static final String SAVED_TOKEN_KEY = "token";
    public static final String SAVED_OBJECTID_KEY = "objectId";
    public static final int MOVIES_POPULAR_METHOD = Request.Method.GET;

    public static final String NIKOLA_GITHUB_PROFILE = "https://github.com/NikolaBorislavovHristov";
    public static final String MARTIN_GITHUB_PROFILE = "https://github.com/matioa";

    public static final String NO_INTERNET_MESSAGE = "Unable to connect to the Internet";
    public static final String SEARCH_ENDPOINT = "http://api.themoviedb.org/3/search/movie?api_key=%s&query=%s&page=%d";

    public static final String IMGUR_CLIENT_ID = "Client-ID bc967b7720211cf";
    public static final String IMGUR_BODY = "{\"image\":\"%s\"}";
}
