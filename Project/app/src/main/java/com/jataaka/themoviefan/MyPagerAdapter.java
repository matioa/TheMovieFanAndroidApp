package com.jataaka.themoviefan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jataaka.themoviefan.fragments.AboutFragment;
import com.jataaka.themoviefan.fragments.FavoritesFragment;
import com.jataaka.themoviefan.fragments.HomeFragment;
import com.jataaka.themoviefan.fragments.LoginFragment;
import com.jataaka.themoviefan.fragments.LogoutFragment;
import com.jataaka.themoviefan.fragments.RegisterFragment;
import com.jataaka.themoviefan.fragments.SettingsFragment;
import com.jataaka.themoviefan.fragments.WatchlistFragment;

/**
 * Created by nikola on 16/01/2016.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        switch (position){
            case GlobalConstants.HomeFragmentIndex:
                return new HomeFragment();
            case GlobalConstants.FavoritesFragmentIndex:
                return new FavoritesFragment();
            case GlobalConstants.WatchlistFragmentIndex:
                return new WatchlistFragment();
            case GlobalConstants.LoginFragmentIndex:
                return new LoginFragment();
            case GlobalConstants.LogoutFragmentIndex:
                return new LogoutFragment();
            case GlobalConstants.SettingsFragmentIndex:
                return new SettingsFragment();
            case GlobalConstants.AboutFragmentIndex:
                return new AboutFragment();
            case GlobalConstants.RegisterFragmentIndex:
                return new RegisterFragment();
        }

        return null;
    }



    @Override
    public int getCount() {
        return 8;
    }
}
