package com.jataaka.themoviefan;

import android.database.Cursor;
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
import android.widget.TextView;

import com.jataaka.themoviefan.data.DbActions;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(GlobalConstants.HomeFragmentIndex);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home_page:
                viewPager.setCurrentItem(GlobalConstants.HomeFragmentIndex);
                TextView sqlText;

                // sample insert and retreive of data from the SQLite database
                DbActions db = new DbActions(getApplicationContext());
                db.addRecord("someId", "sampleUser");

                sqlText = (TextView) findViewById(R.id.text_view);
                Cursor resultData = db.getValues();
                sqlText.setText(resultData.getColumnName(0));
                break;
            case R.id.favorites:
                viewPager.setCurrentItem(GlobalConstants.FavoritesFragmentIndex);
                break;
            case R.id.watchlist:
                viewPager.setCurrentItem(GlobalConstants.WatchlistFragmentIndex);
                break;
            case R.id.logout:
                viewPager.setCurrentItem(GlobalConstants.LogoutFragmentIndex);
                break;
            case R.id.login:
                viewPager.setCurrentItem(GlobalConstants.LoginFragmentIndex);
                break;
            case R.id.settings:
                viewPager.setCurrentItem(GlobalConstants.SettingsFragmentIndex);
                break;
            case R.id.about:
                viewPager.setCurrentItem(GlobalConstants.AboutFragmentIndex);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
