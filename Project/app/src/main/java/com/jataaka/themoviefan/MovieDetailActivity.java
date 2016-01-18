package com.jataaka.themoviefan;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.jataaka.themoviefan.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener, Listener<JSONObject>, Response.ErrorListener  {
    private Movie movie;
    private FloatingActionButton likeBtn;
    private Button watchlistBtn;
    private String username;
    private String token;
    private JSONArray likedMovies;
    private JSONArray watchlistMovies;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.movie = (Movie) this.getIntent().getSerializableExtra("Movie");

        NetworkImageView movieCover = (NetworkImageView) this.findViewById(R.id.bigImage);
        TextView movieTitle = (TextView) this.findViewById(R.id.movie_title);
        TextView movieVote = (TextView) this.findViewById(R.id.movie_vote);
        TextView movieYear = (TextView) this.findViewById(R.id.movie_year);
        TextView movieOverview = (TextView) this.findViewById(R.id.movie_overview);
        this.likeBtn = (FloatingActionButton) this.findViewById(R.id.btn_like);
        this.likeBtn.setOnClickListener(this);
        this.watchlistBtn = (Button) this.findViewById(R.id.btn_watchlist);
        this.watchlistBtn.setOnClickListener(this);

        movieCover.setImageUrl(this.movie.getThumbnailUrl(), HttpRequestQueue.getInstance(this).getImageLoader());
        movieTitle.setText(movie.getTitle());
        movieVote.setText("Rating: "+String.valueOf(movie.getVoteAvarage())+" by "+String.valueOf(movie.getVoteCount())+" users");
        movieOverview.setText(movie.getOverview());
        movieYear.setText("Release year: " + movie.getYear());

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        this.username = this.getIntent().getStringExtra("username");
        this.token = this.getIntent().getStringExtra("token");
        this.objectId = this.getIntent().getStringExtra("objectId");
        if (!this.token.isEmpty()) {
            this.getLikedMovies();
            this.getWatchlistMovies();
        }
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_like:
                this.likeThisMovie();
                break;
            case R.id.btn_watchlist:
                this.watchThisMovie();
                break;
        }
    }

    private void likeThisMovie() {
        if (this.token.isEmpty()) {
            this.showMessage("You are not loggedin");
            return;
        }
        String endpoint = "https://api.parse.com/1/users/" + this.objectId;

        this.likedMovies.put(this.movie.getId());
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject();
            jsonBody.put("liked", this.likedMovies);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, endpoint, jsonBody, this, this) {
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

    private void watchThisMovie() {
        if (this.token.isEmpty()) {
            this.showMessage("You are not loggedin");
            return;
        }
        String endpoint = "https://api.parse.com/1/users/" + this.objectId;

        this.watchlistMovies.put(this.movie.getId());
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject();
            jsonBody.put("watchlist", this.watchlistMovies);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, endpoint, jsonBody, this, this) {
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

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONObject response) {
        boolean liked = response.has("liked");
        boolean watchlist = response.has("watchlist");
        if (liked) {
            this.showMessage("Movie added to Favourites");
        }
        if (watchlist) {
            this.showMessage("Movie added to Watchlist");
        }
    }

    private void getLikedMovies() {
        String endpoint = String.format("https://api.parse.com/1/users/me");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, endpoint, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    likedMovies = json.getJSONArray("liked");
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

    private void getWatchlistMovies() {
        String endpoint = String.format("https://api.parse.com/1/users/me");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, endpoint, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    watchlistMovies = json.getJSONArray("watchlist");
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
}