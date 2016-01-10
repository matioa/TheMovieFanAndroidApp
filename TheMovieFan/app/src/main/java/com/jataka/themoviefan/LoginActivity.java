package com.jataka.themoviefan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponseListener  {
    private Button goToRegisterBtn;
    private Button loginBtn;
    private EditText loginUsernameTxt;
    private EditText loginPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.goToRegisterBtn = (Button) this.findViewById(R.id.goToRegisterBtn);
        this.loginBtn = (Button) this.findViewById(R.id.loginBtn);
        this.loginUsernameTxt = (EditText) this.findViewById(R.id.loginUsernameTxt);
        this.loginPasswordTxt = (EditText) this.findViewById(R.id.loginPasswordTxt);

        this.loginBtn.setOnClickListener(this);
        this.goToRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToRegisterBtn:
                this.goToRegisterBtnOnClick();
                break;
            case R.id.loginBtn:
                this.loginBtnOnClick();
                break;
        }
    }

    @Override
    public void onProcessFinish(String result) {
        this.showMessage(result);
    }

    private void goToRegisterBtnOnClick() {
        Intent goToRegisterIntent = new Intent(this, RegisterActivity.class);
        this.startActivity(goToRegisterIntent);
    }

    private void loginBtnOnClick() {
        String username = String.valueOf(this.loginUsernameTxt.getText());
        String password = String.valueOf(this.loginPasswordTxt.getText());

        if (username.isEmpty()){
            this.showMessage(MessageConstants.USERNAME_REQUIRED_EN);
        }

        if (password.isEmpty()){
            this.showMessage(MessageConstants.PASSWORD_REQUIRED_EN);
        }

        HttpAsyncRequest loginReq = new HttpAsyncRequest();

        loginReq.setOnResponseListener(this);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(ParseComConstants.HEADER_RESTAPI_KEY_NAME, ParseComConstants.HEADER_RESTAPI_KEY_VALUE);
        headers.put(ParseComConstants.HEADER_APP_ID_NAME, ParseComConstants.HEADER_APP_ID_VALUE);

        String endpoint = String.format(ParseComConstants.LOGIN_USER_ENDPOINT, username, password);

        loginReq.execute(endpoint, ParseComConstants.LOGIN_USER_METHOD, headers);
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
