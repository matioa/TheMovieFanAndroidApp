package com.jataaka.themoviefan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.HttpRequestQueue;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputPasswordConfirm;
    private EditText inputEmail;
    private Button buttonLogin;
    private Button buttonSignup;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainActivity = (MainActivity) view.getContext();
        this.inputUsername = (EditText) view.findViewById(R.id.input_username_reg);
        this.inputPassword = (EditText) view.findViewById(R.id.input_password_reg);
        this.inputPasswordConfirm = (EditText) view.findViewById(R.id.input_passwordConfirm_reg);
        this.buttonSignup = (Button) view.findViewById(R.id.btn_signup);
        this.buttonLogin = (Button) view.findViewById(R.id.btn_goto_login);
        this.inputEmail = (EditText) view.findViewById(R.id.input_email_reg);

        this.buttonLogin.setOnClickListener(this);
        this.buttonSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                this.btnSignupOnClick();
                break;
            case R.id.btn_goto_login:
                this.btnLoginOnClick();
                break;
        }
    }

    private void btnSignupOnClick() {
        if (!this.mainActivity.isOnline()) {
            this.mainActivity.showMessage(GlobalConstants.NO_INTERNET_MESSAGE);
            return;
        }

        final String username = String.valueOf(this.inputUsername.getText());
        final String password = String.valueOf(this.inputPassword.getText());
        final String passwordConfirm = String.valueOf(this.inputPasswordConfirm.getText());
        final String email = String.valueOf(this.inputEmail.getText());

        if (username.isEmpty()){
            this.mainActivity.showMessage(GlobalConstants.USERNAME_REQUIRED_EN);
            return;
        }

        if (password.isEmpty()){
            this.mainActivity.showMessage(GlobalConstants.PASSWORD_REQUIRED_EN);
            return;
        }

        if (email.isEmpty()) {
            this.mainActivity.showMessage("Email is not valid");
            return;
        }

        if (password.compareTo(passwordConfirm) != 0) {
            this.mainActivity.showMessage(GlobalConstants.PASSWORDS_NOT_MATCH_EN);
            return;
        }

        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject(String.format(GlobalConstants.REGISTER_USER_BODY, username, password, email));
        } catch (JSONException e) {
            e.printStackTrace();
        }
            String x = jsonBody.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GlobalConstants.REGISTER_USER_METHOD, GlobalConstants.REGISTER_USER_ENDPOINT, jsonBody, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<String, String>();
                headers.put(GlobalConstants.HEADER_RESTAPI_KEY_NAME,GlobalConstants.HEADER_RESTAPI_KEY_VALUE);
                headers.put(GlobalConstants.HEADER_APP_ID_NAME,GlobalConstants.HEADER_APP_ID_VALUE);
                return headers;
            }
        };

        HttpRequestQueue.getInstance(this.mainActivity).addToRequestQueue(jsonObjectRequest);
    }

    private void btnLoginOnClick() {
        this.mainActivity.navigateViewPagerTo(GlobalConstants.LoginFragmentIndex);
    }

    @Override
    public void onErrorResponse(VolleyError error)  {
        String errorMessage = new String(error.networkResponse.data);
        try {
            JSONObject errorJson = new JSONObject(errorMessage);
            this.mainActivity.showMessage(errorJson.getString("error"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        this.mainActivity.showMessage(GlobalConstants.USER_REGISTERED_EN);
        this.mainActivity.navigateViewPagerTo(GlobalConstants.LoginFragmentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.inputUsername.setText("");
        this.inputPassword.setText("");
        this.inputPasswordConfirm.setText("");
    }
}
