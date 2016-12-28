package com.example.abetrosita.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    Menu itemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this,
                new ArrayList<Photo>());
        recyclerView.setAdapter(flickrRecyclerViewAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                      Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, PhotoDetailActivity.class);
                        i.putExtra(PHOTO_TRANSFER, flickrRecyclerViewAdapter.getPhoto(position));
                        startActivity(i);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Long Clicked", Toast.LENGTH_SHORT).show();
                        //Add code to hide on swipe left or right
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchPhotos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        itemMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.menu_search){
            inflateSearch(itemMenu);
            item.setVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }
}
