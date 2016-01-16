package com.jataaka.themoviefan.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jataaka.themoviefan.R;
import com.jataaka.themoviefan.data.DbActions;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DbActions db = new DbActions(view.getContext().getApplicationContext());
        db.addRecord("someId", "sampleUser");


        TextView sqlText = (TextView) view.findViewById(R.id.text_view);
        Cursor resultData = db.getValues();
        sqlText.setText(resultData.getColumnName(0));
    }
}
