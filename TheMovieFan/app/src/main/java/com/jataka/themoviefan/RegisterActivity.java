package com.jataka.themoviefan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponseListener  {
    private Button registerBtn;
    private EditText registerUsernameTxt;
    private EditText registerPasswordTxt;
    private EditText passwordConfirmTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.registerBtn = (Button) this.findViewById(R.id.registerBtn);
        this.registerUsernameTxt = (EditText) this.findViewById(R.id.registerUsernameTxt);
        this.registerPasswordTxt = (EditText) this.findViewById(R.id.registerPasswordTxt);
        this.passwordConfirmTxt = (EditText) this.findViewById(R.id.passwordConfirmTxt);

        this.registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBtn:
                this.registerOnClick();
                break;
        }
    }

    @Override
    public void onProcessFinish(String result) {
        JSONObject response = null;

        try {
            response = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String error = response.getString(JSONPropertiesConstants.ERRROR_RESPONSE_PROPERTY);
            this.showMessage(error);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String token = response.getString(JSONPropertiesConstants.TOKEN_RESPONSE_PROPERTY);
            //AddToken To DB;
            this.showMessage(MessageConstants.USER_REGISTERED_EN);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void registerOnClick() {
        String username = String.valueOf(this.registerUsernameTxt.getText());
        String password = String.valueOf(this.registerPasswordTxt.getText());
        String passwordConfirm = String.valueOf(this.passwordConfirmTxt.getText());

        if (username.isEmpty()) {
            this.showMessage(MessageConstants.USERNAME_REQUIRED_EN);
        }

        if (password.isEmpty()) {
            this.showMessage(MessageConstants.PASSWORD_REQUIRED_EN);
        }

        if (!password.contentEquals(passwordConfirm)) {
            this.showMessage(MessageConstants.PASSWORDS_NOT_MATCH_EN);
            return;
        }

        HttpAsyncRequest registerReq = new HttpAsyncRequest();

        registerReq.setOnResponseListener(this);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(ParseComConstants.HEADER_RESTAPI_KEY_NAME, ParseComConstants.HEADER_RESTAPI_KEY_VALUE);
        headers.put(ParseComConstants.HEADER_APP_ID_NAME, ParseComConstants.HEADER_APP_ID_VALUE);

        JSONObject body = new JSONObject();
        try {
            body.put(JSONPropertiesConstants.USERNAME_REQUEST_PROPERTY, username);
            body.put(JSONPropertiesConstants.PASSWORD_REQUEST_PROPERTY, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        registerReq.execute(ParseComConstants.REGISTER_USER_ENDPOINT, ParseComConstants.REGISTER_USER_METHOD, headers, body);
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
