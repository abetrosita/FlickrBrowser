package com.example.abetrosita.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AbetRosita on 12/27/2016.
 */

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
    private ArrayList<Photo> photoArrayList;
    private Context context;

    public FlickrRecyclerViewAdapter(Context context, ArrayList<Photo> photoArrayList) {
        this.context = context;
        this.photoArrayList = photoArrayList;

    }

    public void loadPhotoData(ArrayList<Photo> photos){
        photoArrayList = photos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return (null != photoArrayList ? photoArrayList.get(position) : null);
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        return flickrImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder flickrImageViewHolder, int i) {
        Photo photoItem = photoArrayList.get(i);
        Picasso.with(context).load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(flickrImageViewHolder.thumbnail);
        flickrImageViewHolder.title.setText(photoItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return (null != photoArrayList ? photoArrayList.size() : 0);
    }
}
