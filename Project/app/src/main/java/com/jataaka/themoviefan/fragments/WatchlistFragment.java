package com.jataaka.themoviefan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jataaka.themoviefan.CustomListAdapter;
import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.HttpRequestQueue;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.MovieDetailActivity;
import com.jataaka.themoviefan.R;
import com.jataaka.themoviefan.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchlistFragment extends Fragment implements ListView.OnItemClickListener, Response.Listener<String>, Response.ErrorListener {
    private List<Movie> movieList;
    private ListView listView;
    private MainActivity mainActivity;
    private CustomListAdapter adapter;
    private JSONArray watchlistMovies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mainActivity = (MainActivity) view.getContext();
        this.movieList = new ArrayList<Movie>();

        this.listView = (ListView) view.findViewById(R.id.watchlist_list);
        this.adapter = new CustomListAdapter(this.mainActivity, this.movieList);
        this.listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!this.mainActivity.isOnline()) {
            this.mainActivity.showMessage(GlobalConstants.NO_INTERNET_MESSAGE);
            return;
        }

        this.movieList.clear();
        this.getWatchlistMovies();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent detailViewIntent = new Intent(this.mainActivity, MovieDetailActivity.class);
        detailViewIntent.putExtra("Movie", this.movieList.get(position));
        detailViewIntent.putExtra("token", this.mainActivity.user.getSessionToken());
        detailViewIntent.putExtra("username", this.mainActivity.user.getUsername());
        detailViewIntent.putExtra("objectId", this.mainActivity.user.getObjectId());

        this.startActivity(detailViewIntent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            int id = obj.getInt("id");
            String title = obj.getString("title");
            String overview = obj.getString("overview");
            String imgUrl = "http://image.tmdb.org/t/p/w300" + obj.getString("poster_path");
            double rating = obj.getDouble("vote_average");
            String date = obj.getString("release_date");
            int voteCount = obj.getInt("vote_count");
            JSONArray genres = obj.getJSONArray("genres");
            ArrayList<Integer> genresArray = new ArrayList<>();
            for (int j = 0; j < genres.length(); j++) {
                JSONObject genre = genres.getJSONObject(j);
                genresArray.add(genre.getInt("id"));
            }
            Movie movie = new Movie(id, title, imgUrl, overview, date, rating, voteCount, genresArray);
            movieList.add(movie);
            this.adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        for (int i = 0; i < watchlistMovies.length(); i++) {
            String endpoint = null;
            try {
                endpoint = String.format(GlobalConstants.MOVIE_BYID_ENDPOINT, watchlistMovies.getInt(i), GlobalConstants.HEADER_TMDB_API_KEY_NAME, GlobalConstants.HEADER_TMDB_API_KEY_VALUE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.GET, endpoint, this, this);
            HttpRequestQueue.getInstance(this.mainActivity).addToRequestQueue(stringRequest);
        }

    }

    private void getWatchlistMovies() {
        final String endpoint = "https://api.parse.com/1/users/me";
        final String token = this.mainActivity.user.getSessionToken();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, endpoint, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    watchlistMovies = json.getJSONArray("watchlist");
                    getData();
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

        HttpRequestQueue.getInstance(this.mainActivity).addToRequestQueue(stringRequest);
    }
}
