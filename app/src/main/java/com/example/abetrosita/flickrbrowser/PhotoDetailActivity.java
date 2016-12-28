package com.example.abetrosita.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        super.activateToolbarWithHomeEnabled();

        Intent i = getIntent();
        Photo photo = (Photo) i.getSerializableExtra(PHOTO_TRANSFER);

        TextView photoTitle = (TextView) findViewById(R.id.photoTitle);
        TextView photoAuthor = (TextView) findViewById(R.id.photoAuthor);
        TextView photoTags = (TextView) findViewById(R.id.photoTags);
        ImageView photoImage = (ImageView) findViewById(R.id.photoImage);

        photoTitle.setText("Title: " + photo.getTitle());
        photoAuthor.setText("Author: " + photo.getAuthor());
        photoTags.setText("Tags: " + photo.getTags());

        String link = photo.getImage().replaceFirst("_m.", "_b.");
        Picasso.with(this).load(link)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
