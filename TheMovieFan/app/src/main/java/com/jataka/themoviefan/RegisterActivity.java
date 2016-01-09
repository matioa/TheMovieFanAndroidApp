package com.jataka.themoviefan;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button registerBtn;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private EditText passwordConfirmTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.registerBtn = (Button) this.findViewById(R.id.registerBtn);
        this.usernameTxt = (EditText) this.findViewById(R.id.registerUsernameTxt);
        this.passwordTxt = (EditText) this.findViewById(R.id.registerPasswordTxt);
        this.passwordConfirmTxt = (EditText) this.findViewById(R.id.passwordConfirmTxt);

        this.registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = String.valueOf(this.usernameTxt.getText());

        String password = String.valueOf(this.passwordTxt.getText());

        try {
            this.Register(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Register(String username, String password) throws IOException {

        HttpAsyncRequester move = new HttpAsyncRequester();
        move.execute();
    }


}
