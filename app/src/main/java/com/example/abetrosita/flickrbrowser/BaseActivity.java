package com.example.abetrosita.flickrbrowser;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by AbetRosita on 12/27/2016.
 */

public class BaseActivity extends AppCompatActivity {
    ArrayList<Photo> photoArrayList;
    RecyclerView recyclerView;
    FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;
    SearchView searchView;

    private Toolbar toolbar;
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    protected Toolbar activateToolbar(){
        if(toolbar == null){
            toolbar = (Toolbar) findViewById(R.id.app_bar);
            if(toolbar != null){
                setSupportActionBar(toolbar);
            }
        }
        return toolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled(){
        activateToolbar();
        if(toolbar !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        return toolbar;
    }


    public boolean inflateSearch(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem menuItem = menu.findItem(R.id.search_view);
        searchView = (SearchView) menuItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString(FLICKR_QUERY, query).commit();
                searchView.clearFocus();
//                finish();
                searchPhotos();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
//                finish();
                searchPhotos();
                return false;
            }
        });

        return true;
    }

    public void searchPhotos(){

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String query = getSavedPreferenceData(FLICKR_QUERY);

        if(query.length() > 0){
            if(searchView != null){
                searchView.onActionViewCollapsed();
            }
            MainActivity.ProcessPhotos processPhotos = new MainActivity.ProcessPhotos(query, true);
            processPhotos.execute();
        }

    }

    private String getSavedPreferenceData(String key){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
    }

    public class ProcessPhotos extends JsonData{

        public ProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }

        public void  execute(){
            super.execute();
        }

        @Override
        public void processResult() {
            super.processResult();

            photoArrayList = getPhotos();
            flickrRecyclerViewAdapter.loadPhotoData(photoArrayList);
        }
    }

}
