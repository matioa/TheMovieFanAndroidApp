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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jataaka.themoviefan.CustomListAdapter;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;
import com.jataaka.themoviefan.data.DbActions;

public class HomeFragment extends Fragment {
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

        DbActions db = new DbActions(view.getContext().getApplicationContext());
        db.addRecord("someId", "sampleUser");


        TextView sqlText = (TextView) view.findViewById(R.id.text_view);
        Cursor resultData = db.getValues();
        sqlText.setText(resultData.getColumnName(0));
    }
}
