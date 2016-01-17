package com.jataaka.themoviefan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jataaka.themoviefan.GlobalConstants;
import com.jataaka.themoviefan.MainActivity;
import com.jataaka.themoviefan.R;

public class LogoutFragment extends Fragment implements View.OnClickListener {
    private MainActivity mainActivity;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainActivity = (MainActivity) view.getContext();
        this.btnLogout = (Button) view.findViewById(R.id.btn_logout);

        this.btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                this.btnLogoutOnClick();
                break;
        }
    }

    private void btnLogoutOnClick() {
        this.mainActivity.logoutUser();
        this.mainActivity.showMessage(GlobalConstants.USER_LOGGEDOUT_EN);
        this.mainActivity.navigateViewPagerTo(GlobalConstants.HomeFragmentIndex);
    }
}
