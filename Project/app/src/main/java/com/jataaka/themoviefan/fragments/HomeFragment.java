package com.jataaka.themoviefan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jataaka.themoviefan.CustomListAdapter;
import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.HttpRequestQueue;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;
import com.jataaka.themoviefan.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ListView.OnScrollListener, ListView.OnItemClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private List<Movie> movieList;
    private ListView listView;
    private MainActivity mainActivity;
    private CustomListAdapter adapter;
    private int currentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mainActivity = (MainActivity) view.getContext();
        this.currentPage = 1;
        this.movieList = new ArrayList<Movie>();
        this.getData();

        this.listView = (ListView) view.findViewById(R.id.list);
        this.adapter = new CustomListAdapter(this.mainActivity, this.movieList);
        this.listView.setAdapter(adapter);
        this.listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);

//        DbActions db = new DbActions(view.getContext().getApplicationContext());
//        db.addRecord("someId", "sampleUser");
//
//        TextView sqlText = (TextView) view.findViewById(R.id.text_view);
//        Cursor resultData = db.getValues();
//        sqlText.setText(resultData.getColumnName(0));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view.getLastVisiblePosition() >= (this.adapter.getCount() - 5)) {
            this.currentPage++;
            this.getData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //DoNothing;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray movies = response.getJSONArray("results");
            for (int i = 0; i < movies.length(); i++) {
                JSONObject obj  = movies.getJSONObject(i);
                Movie movie = new Movie();
                movie.setTitle(obj.getString("title"));
                movie.setOverview(obj.getString("overview"));
                String imgUrl = "http://image.tmdb.org/t/p/w300" + obj.getString("poster_path");
                movie.setThumbnailUrl(imgUrl);
                movie.setRating(((Number) obj.get("vote_average")).doubleValue());
                movieList.add(movie);
            }

            this.adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String Slecteditem = this.movieList.get(position).getTitle();
        this.mainActivity.showMessage(Slecteditem);
    }

    private void getData() {
        String url = String.format(GlobalConstants.MOVIES_POPULAR_ENDPOINT, GlobalConstants.HEADER_TMDB_API_KEY_NAME, GlobalConstants.HEADER_TMDB_API_KEY_VALUE, this.currentPage);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        HttpRequestQueue.getInstance(this.mainActivity).addToRequestQueue(jsObjRequest);
    }
}