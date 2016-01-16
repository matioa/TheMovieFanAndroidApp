package com.jataaka.themoviefan.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jataaka.themoviefan.CustomListAdapter;
import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.HttpRequestQueue;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;
import com.jataaka.themoviefan.data.DbActions;

import org.json.JSONObject;

public class HomeFragment extends Fragment {
    ImageView mImageView;


    ListView list;
    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    Integer[] imgid={
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
            R.drawable.ic_test_image,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView mTxtDisplay = (TextView) view.findViewById(R.id.text_view);
        String endpoint = String.format(GlobalConstants.MOVIES_POPULAR_ENDPOINT,GlobalConstants.HEADER_TMDB_API_KEY_NAME, GlobalConstants.HEADER_TMDB_API_KEY_VALUE);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mTxtDisplay.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        HttpRequestQueue.getInstance(view.getContext()).addToRequestQueue(jsObjRequest);


        CustomListAdapter adapter=new CustomListAdapter((MainActivity)view.getContext(), itemname, imgid);
        list=(ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                Toast.makeText(view.getContext().getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

//        DbActions db = new DbActions(view.getContext().getApplicationContext());
//        db.addRecord("someId", "sampleUser");
//
//
//        TextView sqlText = (TextView) view.findViewById(R.id.text_view);
//        Cursor resultData = db.getValues();
//        sqlText.setText(resultData.getColumnName(0));
    }
}
