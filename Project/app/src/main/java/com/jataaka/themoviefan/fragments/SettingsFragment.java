package com.jataaka.themoviefan.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.MovieDetailActivity;
import com.jataaka.themoviefan.ProfileActivity;
import com.jataaka.themoviefan.R;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private Button goToAccount;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainActivity = (MainActivity) view.getContext();

        this.goToAccount = (Button) view.findViewById(R.id.go_to_account);
        this.goToAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_account:
                this.goToAccount();
                break;
        }
    }

    private void goToAccount() {
        if (!this.mainActivity.user.getUserStatus()) {
            this.mainActivity.showMessage("You are not loggedIn");
            return;
        }
        Intent goToAccountIntent = new Intent(this.mainActivity, ProfileActivity.class);
        goToAccountIntent.putExtra("token", this.mainActivity.user.getSessionToken());
        goToAccountIntent.putExtra("objectId", this.mainActivity.user.getObjectId());
        this.startActivity(goToAccountIntent);
    }
}
