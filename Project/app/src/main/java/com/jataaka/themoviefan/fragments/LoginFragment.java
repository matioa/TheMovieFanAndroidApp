package com.jataaka.themoviefan.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.HttpRequestQueue;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;
import com.jataaka.themoviefan.data.DbActions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {
    EditText inputEmail;
    EditText inputPassword;
    Button buttonLogin;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) view.getContext();
        inputEmail = (EditText) view.findViewById(R.id.input_email);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        buttonLogin = (Button) view.findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                this.btnLoginOnClick(view);
                break;
        }
    }

    private void btnLoginOnClick(View view) {
        String email = String.valueOf(inputEmail.getText());
        String password = String.valueOf(inputPassword.getText());

        if (email.isEmpty()){
            mainActivity.showMessage(GlobalConstants.USERNAME_REQUIRED_EN);
        }

        if (password.isEmpty()){
            mainActivity.showMessage(GlobalConstants.PASSWORD_REQUIRED_EN);
        }


        String endpoint = String.format(GlobalConstants.LOGIN_USER_ENDPOINT, email, password);

        StringRequest stringRequest = new StringRequest(GlobalConstants.LOGIN_USER_METHOD, endpoint, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<String, String>();
                headers.put(GlobalConstants.HEADER_RESTAPI_KEY_NAME, GlobalConstants.HEADER_RESTAPI_KEY_VALUE);
                headers.put(GlobalConstants.HEADER_APP_ID_NAME, GlobalConstants.HEADER_APP_ID_VALUE);
                return headers;
            }
        };

        HttpRequestQueue.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // // TODO: 16/01/2016 switch by errorcode
        mainActivity.showMessage("Error is: " + error.networkResponse.data.toString());
    }

    @Override
    public void onResponse(String response) {
        // TODO: 16/01/2016 add tpooken to db
        // TODO: 16/01/2016 navigate to homepage
        // TODO: 16/01/2016 hode login and show logout favorites and watchlists
        mainActivity.showMessage("Response is: ");
    }
}
