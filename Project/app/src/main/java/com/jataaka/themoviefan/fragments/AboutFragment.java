package com.jataaka.themoviefan.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class AboutFragment extends Fragment implements View.OnClickListener {
    private MainActivity mainActivity;
    private Button nikolaLink;
    private Button martinLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainActivity = (MainActivity) view.getContext();

        this.nikolaLink = (Button) view.findViewById(R.id.go_to_nikola);
        this.martinLink = (Button) view.findViewById(R.id.go_to_martin);

        this.nikolaLink.setOnClickListener(this);
        this.martinLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_nikola:
                this.viewContact(GlobalConstants.NIKOLA_GITHUB_PROFILE);
                break;
            case R.id.go_to_martin:
                this.viewContact(GlobalConstants.MARTIN_GITHUB_PROFILE);
                break;
        }
    }

    private void viewContact(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        this.startActivity(intent);
    }
}
