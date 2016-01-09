package com.jataka.themoviefan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if not logged in
        Intent logInIntent = new Intent(this, LoginActivity.class);
        this.startActivity(logInIntent);
    }
}
