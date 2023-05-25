package com.alejandro.openweatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alejandro.openweatherapp.model.FavouriteList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alejandro.openweatherapp.ui.main.SectionsPagerAdapter;
import com.alejandro.openweatherapp.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FavouriteList favsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_star);
        tabs.getTabAt(1).setIcon(R.drawable.ic_search);
        favsList = new FavouriteList(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.about_us) {
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                favsList.addItem(data.getStringExtra("cityName"));
                try {
// Save list for later
                    favsList.saveToFile();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                FavsFragment.adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult

    @Override
    protected void onResume() {
        super.onResume();
        try {
// Attempt to load a previously saved list
            favsList.readFromFile();
            if(FavsFragment.adapter!=null){
                FavsFragment.adapter.notifyDataSetChanged();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
// Save list for later
            favsList.saveToFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FavouriteList getFavsList() {
        return favsList;
    }



}

