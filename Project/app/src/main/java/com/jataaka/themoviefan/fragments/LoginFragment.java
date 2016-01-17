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
import com.android.volley.toolbox.StringRequest;
import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.HttpRequestQueue;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {
    private final String USERNAME_RESPONSE_PROPERTY = "username";
    private final String TOKEN_RESPONSE_PROPERTY = "sessionToken";

    private EditText inputUsername;
    private EditText inputPassword;
    private Button buttonLogin;
    private Button buttonSignup;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainActivity = (MainActivity) view.getContext();
        this.inputUsername = (EditText) view.findViewById(R.id.input_username);
        this.inputPassword = (EditText) view.findViewById(R.id.input_password);
        this.buttonLogin = (Button) view.findViewById(R.id.btn_login);
        this.buttonSignup = (Button) view.findViewById(R.id.btn_goto_signup);

        this.buttonLogin.setOnClickListener(this);
        this.buttonSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                this.btnLoginOnClick();
                break;
            case R.id.btn_goto_signup:
                this.btnSignupOnClick();
                break;
        }
    }

    private void btnSignupOnClick() {
        this.mainActivity.navigateViewPagerTo(GlobalConstants.RegisterFragmentIndex);
    }

    private void btnLoginOnClick() {
        final String username = String.valueOf(this.inputUsername.getText());
        final String password = String.valueOf(this.inputPassword.getText());

        if (username.isEmpty()){
            this.mainActivity.showMessage(GlobalConstants.USERNAME_REQUIRED_EN);
        }

        if (password.isEmpty()){
            this.mainActivity.showMessage(GlobalConstants.PASSWORD_REQUIRED_EN);
        }

        String endpoint = String.format(GlobalConstants.LOGIN_USER_ENDPOINT, username, password);

        StringRequest stringRequest = new StringRequest(GlobalConstants.LOGIN_USER_METHOD, endpoint, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(GlobalConstants.HEADER_RESTAPI_KEY_NAME, GlobalConstants.HEADER_RESTAPI_KEY_VALUE);
                headers.put(GlobalConstants.HEADER_APP_ID_NAME, GlobalConstants.HEADER_APP_ID_VALUE);
                return headers;
            }
        };

        HttpRequestQueue.getInstance(this.mainActivity).addToRequestQueue(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // // TODO: 16/01/2016 switch by statusCode
        this.mainActivity.showMessage("Error is: " + error.networkResponse.statusCode);
    }

    @Override
    public void onResponse(String response) {
        // TODO: 16/01/2016 add tpooken to db
        // TODO: 16/01/2016 navigate to homepage

        try {
            JSONObject responseAsJSON = new JSONObject(response);
            String username = responseAsJSON.getString(this.USERNAME_RESPONSE_PROPERTY);
            String token = responseAsJSON.getString(this.TOKEN_RESPONSE_PROPERTY);

            this.mainActivity.loginUser(username, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.mainActivity.showMessage(GlobalConstants.USER_LOGGEDIN_EN);
        this.mainActivity.navigateViewPagerTo(GlobalConstants.HomeFragmentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.inputUsername.setText("");
        this.inputPassword.setText("");
    }
}
