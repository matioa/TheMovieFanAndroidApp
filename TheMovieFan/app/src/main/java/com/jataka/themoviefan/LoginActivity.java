package com.jataka.themoviefan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button goToRegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);this.goToRegisterBtn = (Button) this.findViewById(R.id.goToRegisterBtn);

        this.goToRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToRegisterBtn:
                this.goToRegisterBtnOnClick();
                break;
            case R.id.loginBtn:
                break;
        }
    }

    private void goToRegisterBtnOnClick() {
        Intent goToRegisterIntent = new Intent(this, RegisterActivity.class);
        this.startActivity(goToRegisterIntent);
    }
}
