package com.jataka.themoviefan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jataka.themoviefan.data.DbActions;
import com.jataka.themoviefan.data.DbContract;
import com.jataka.themoviefan.data.DbInit;

public class MainActivity extends AppCompatActivity {
    private TextView sqlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if not logged in
        Intent logInIntent = new Intent(this, LoginActivity2.class);
        this.startActivity(logInIntent);

        // sample insert and retreive of data from the SQLite database
/*        DbActions db = new DbActions(getApplicationContext());
        db.addRecord("someId", "sampleUser");

        this.sqlText = (TextView) this.findViewById(R.id.SqlDataView);
        Cursor resultData = db.getValues();
        this.sqlText.setText(resultData.toString());*/
    }
}
