package com.jataaka.themoviefan;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nikola on 16/01/2016.
 */
public class User {
    private boolean isLoggedIn;
    private String sessionToken;
    private String username;
    private String objectId;
    private MainActivity mainActivity;
    private OnStatusChangeListener listener;

    public User(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        SharedPreferences sharedPref = this.mainActivity.getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString(GlobalConstants.SAVED_USERNAME_KEY, "");
        String token = sharedPref.getString(GlobalConstants.SAVED_TOKEN_KEY, "");
        String objectId = sharedPref.getString(GlobalConstants.SAVED_OBJECTID_KEY, "");

        if (!username.isEmpty() && !token.isEmpty() && !objectId.isEmpty()) {
            this.loginUser(username, token, objectId);
        } else {
            this.setUserStatus(false);
            this.username = "";
            this.sessionToken = "";
            this.objectId = "";
        }
    }

    public void setUserStatus(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;

        if (this.listener != null) {
            this.listener.onStatusChangeListener(this.isLoggedIn);
        }
    }

    public void loginUser(String username, String sessionToken, String objectId) {
        SharedPreferences sharedPref = this.mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(GlobalConstants.SAVED_USERNAME_KEY, username);
        editor.putString(GlobalConstants.SAVED_TOKEN_KEY, sessionToken);
        editor.putString(GlobalConstants.SAVED_OBJECTID_KEY, objectId);

        this.username = username;
        this.sessionToken = sessionToken;
        this.objectId = objectId;
        this.setUserStatus(true);
        editor.apply();
    }

    public void logoutUser() {
        SharedPreferences sharedPref = this.mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(GlobalConstants.SAVED_USERNAME_KEY);
        editor.remove(GlobalConstants.SAVED_TOKEN_KEY);
        editor.remove(GlobalConstants.SAVED_OBJECTID_KEY);

        this.username = "";
        this.sessionToken = "";
        this.objectId = "";
        this.setUserStatus(false);
        editor.apply();
    }

    public boolean getUserStatus() {
        return this.isLoggedIn;
    }

    public String getUsername() {
        return this.username;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setOnStatusChangeListener(OnStatusChangeListener listener) {
        this.listener = listener;
        this.listener.onStatusChangeListener(this.isLoggedIn);
    }

    public static interface OnStatusChangeListener {
        void onStatusChangeListener(boolean userStatus);
    }
}