package com.example.magnus.menufragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new SchemaFragment()).commit();
            setTitle("Schema");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_schema) {
            fragment = new SchemaFragment();
            fm.replace(R.id.content, fragment);
            fm.commit();
            setTitle(item.getTitle());
        }
        else if (id == R.id.nav_ekonomi) {
            fragment = new EkonomiFragment();
            fm.replace(R.id.content, fragment);
            fm.commit();
            setTitle(item.getTitle());
        }
        else if (id == R.id.nav_lager) {
            fragment = new LagerFragment();
            fm.replace(R.id.content, fragment);
            fm.commit();
            setTitle(item.getTitle());
        }
        else if (id == R.id.nav_annons) {
            fragment = new AnnonsFragment();
            fm.replace(R.id.content, fragment);
            fm.commit();
            setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void schemaUpdateTime(View view) {
        Toast.makeText(view.getContext(), "edit annons", Toast.LENGTH_LONG).show();
        Fragment fragment;
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.schema_edit:

                fragment = new SchemaUpdateTimeFragment();
                fm.replace(R.id.content, fragment);
                fm.addToBackStack(null);
                fm.commit();

                break;
        }
    }
}

