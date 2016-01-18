package com.jataaka.themoviefan;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener  {
    private NetworkImageView profilePic;
    private Button submitBtn;
    private TextView email;
    private String token;
    private String objectId;
    private Context context;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);

        this.context = this;
        this.token = getIntent().getStringExtra("token");
        this.objectId = getIntent().getStringExtra("objectId");

        this.profilePic = (NetworkImageView) this.findViewById(R.id.profile_pic);
        this.submitBtn = (Button) this.findViewById(R.id.submit);
        this.email = (TextView) this.findViewById(R.id.acc_email);

        this.getUserInfo();

        this.profilePic.setOnClickListener(this);
        this.submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_pic:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 5);
                break;
            case R.id.submit:
                this.submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String image = this.encodeTobase64(imageBitmap);
            this.uploadInIMGUR(image);
        }
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getUserInfo() {
        String endpoint = "https://api.parse.com/1/users/me";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, endpoint, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    email.setText(json.getString("email"));
                    String pictureUrl = json.getString("profilePicURL");
                    if (!pictureUrl.isEmpty()) {
                        profilePic.setImageUrl(pictureUrl, HttpRequestQueue.getInstance(context).getImageLoader());
                    } else {
                        profilePic.setImageUrl("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSNjJdOQWyYxc1aqcoV5KmV8TRRYopG33qlkhr9NVcsQzHXEEMC", HttpRequestQueue.getInstance(context).getImageLoader());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(GlobalConstants.HEADER_RESTAPI_KEY_NAME, GlobalConstants.HEADER_RESTAPI_KEY_VALUE);
                headers.put(GlobalConstants.HEADER_APP_ID_NAME, GlobalConstants.HEADER_APP_ID_VALUE);
                headers.put("X-Parse-Session-Token", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        HttpRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public void uploadInIMGUR(String image) {
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject(String.format(GlobalConstants.IMGUR_BODY, image));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://api.imgur.com/3/image", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            String link = data.getString("link");
                            url = link;
                            profilePic.setImageUrl(link, HttpRequestQueue.getInstance(context).getImageLoader());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, this) {

            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", GlobalConstants.IMGUR_CLIENT_ID);
                return headers;
            }
        };

        HttpRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void submit() {
        String endpoint = "https://api.parse.com/1/users/" + this.objectId;

        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject();
            jsonBody.put("email", String.valueOf(this.email.getText()));
            jsonBody.put("profilePicURL", this.url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, endpoint, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showMessage("Changes apllied!");
                    }
                }, this) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<String, String>();
                headers.put(GlobalConstants.HEADER_RESTAPI_KEY_NAME,GlobalConstants.HEADER_RESTAPI_KEY_VALUE);
                headers.put(GlobalConstants.HEADER_APP_ID_NAME,GlobalConstants.HEADER_APP_ID_VALUE);
                headers.put("X-Parse-Session-Token", token);
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };

        HttpRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
