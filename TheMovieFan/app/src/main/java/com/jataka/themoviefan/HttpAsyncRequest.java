package com.jataka.themoviefan;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Nikola on 10.01.2016.
 */
public class HttpAsyncRequest extends AsyncTask<Object,Void,String> {
    private AsyncResponseListener listener;

    public void setOnResponseListener(AsyncResponseListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Object... params) {
        String result = null;
        HttpURLConnection connection = null;

        if (params.length >= 1) {
            try {
                URL url = new URL(params[0].toString());
                connection = (HttpURLConnection) url.openConnection();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (params.length >= 2) {
            try {
                connection.setRequestMethod(params[1].toString());
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        }

        if (params.length >= 3) {
            HashMap<String, String> headers = (HashMap<String, String>)params[2];
            for (HashMap.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        if (params.length >= 4) {
            String bodyRaw =  params[3].toString();
            try {
                byte[] bodyInBytes = bodyRaw.getBytes("UTF-8");
                OutputStream bodyStream = connection.getOutputStream();
                bodyStream.write(bodyInBytes);
                bodyStream.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            InputStream in = null;
            try {
                in = connection.getInputStream();
            } catch (IOException e){
                in = connection.getErrorStream();
            }

            result = responseStreamToString(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        this.listener.onProcessFinish(result);
    }

    private String responseStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(stream);
        int data = isr.read();
        while (data != -1) {
            char current = (char) data;
            result.append(current);
            data = isr.read();
        }

        return result.toString();
    }
}