package com.jataka.themoviefan;

/**
 * Created by Nikola on 9.1.2016 г..
 */
public class ParseComConstants {
    public static final String HEADER_APP_ID_NAME = "X-Parse-Application-Id";
    public static final String HEADER_APP_ID_VALUE = "7G5mP5LoSpeEkuxMc6UGRHFsgt64EFGvkCH29trM";

    public static final String HEADER_RESTAPI_KEY_NAME = "X-Parse-REST-API-Key";
    public static final String HEADER_RESTAPI_KEY_VALUE = "nVt0bLmNE237BCDMESxTEK2JHKnHprDeUEY6X96s";

    public static final String REGISTER_USER_ENDPOINT = "https://api.parse.com/1/users";
    public static final String REGISTER_USER_METHOD = "POST";

    public static final String LOGIN_USER_ENDPOINT = "https://api.parse.com/1/login?username=%s&password=%s";
    public static final String LOGIN_USER_METHOD = "GET";
}
