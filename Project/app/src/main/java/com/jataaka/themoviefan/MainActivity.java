package com.jataaka.themoviefan;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, User.OnStatusChangeListener {
    private ViewPager viewPager;
    private NavigationView navigationView;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!this.isOnline()) {
            this.setContentView(R.layout.no_internet_layout);
            return;
        }

        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) this.findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);

        this.viewPager = (ViewPager) this.findViewById(R.id.view_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(this.getSupportFragmentManager());
        this.viewPager.setAdapter(adapter);

        this.user = new User(this);
        this.user.setOnStatusChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            this.navigateViewPagerTo(GlobalConstants.SearchFragmentIndex);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home_page:
                this.viewPager.setCurrentItem(GlobalConstants.HomeFragmentIndex);
                break;
            case R.id.favorites:
                this.viewPager.setCurrentItem(GlobalConstants.FavoritesFragmentIndex);
                break;
            case R.id.watchlist:
                this.viewPager.setCurrentItem(GlobalConstants.WatchlistFragmentIndex);
                break;
            case R.id.logout:
                this.viewPager.setCurrentItem(GlobalConstants.LogoutFragmentIndex);
                break;
            case R.id.login:
                this.viewPager.setCurrentItem(GlobalConstants.LoginFragmentIndex);
                break;
            case R.id.register:
                this.viewPager.setCurrentItem(GlobalConstants.RegisterFragmentIndex);
                break;
            case R.id.settings:
                this.viewPager.setCurrentItem(GlobalConstants.SettingsFragmentIndex);
                break;
            case R.id.about:
                this.viewPager.setCurrentItem(GlobalConstants.AboutFragmentIndex);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStatusChangeListener(boolean isLoggedIn) {
        this.navigationView.getMenu().findItem(R.id.favorites).setVisible(isLoggedIn);
        this.navigationView.getMenu().findItem(R.id.watchlist).setVisible(isLoggedIn);
        this.navigationView.getMenu().findItem(R.id.logout).setVisible(isLoggedIn);

        this.navigationView.getMenu().findItem(R.id.register).setVisible(!isLoggedIn);
        this.navigationView.getMenu().findItem(R.id.login).setVisible(!isLoggedIn);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void loginUser(String username, String token, String objId) {
        if (!this.isOnline()) {
            this.showMessage(GlobalConstants.NO_INTERNET_MESSAGE);
            return;
        }
        this.user.loginUser(username, token, objId);
    }

    public void logoutUser() {
        this.user.logoutUser();
    }

    public void navigateViewPagerTo(int fragmentIndex) {
        this.viewPager.setCurrentItem(fragmentIndex);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}