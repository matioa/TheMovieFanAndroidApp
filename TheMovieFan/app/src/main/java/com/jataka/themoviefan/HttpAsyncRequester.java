package com.jataka.themoviefan;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;


import com.jataka.themoviefan.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nikola on .
 */
public class HttpAsyncRequester extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {


        return "";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private String getStringByResponse(InputStream inputStream) throws IOException {

        InputStreamReader byteReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader reader = new BufferedReader(byteReader, 8);
        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        String resultString = sb.toString();

        if(inputStream != null) {
            inputStream.close();
        }
        return resultString;
    }
}
