package com.example.abetrosita.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by AbetRosita on 12/26/2016.
 */

public class JsonData extends RawData {

    private String LOG_TAG = JsonData.class.getSimpleName();
    private ArrayList<Photo> photos;
    private Uri destinationUri;


    public JsonData(String searchCriteria, boolean matchAll) {
        super(null);

        createAndUpdateUri(searchCriteria, matchAll);
        this.photos = new ArrayList<Photo>();
    }

    public void execute(){
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built URI = " + destinationUri.toString());
        super.setRawUrl(destinationUri.toString());
        downloadJsonData.execute(destinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAG_MODE = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK = "nojsoncallback";

        destinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAG_MODE, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK, "1")
                .build();

        return (destinationUri != null);
    }

    public class DownloadJsonData extends DownloadRawData {

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        // TODO: Confirm if this line is needed.
        @Override
        protected String doInBackground(String... params) {
            String[] par = {destinationUri.toString()};
            return super.doInBackground(par);
        }
    }

    public void processResult(){
        if(getDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "Error in raw file");
            return;
        }

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try {
            JSONObject jsonObject = new JSONObject(getUrlData());
            JSONArray jsonArray = jsonObject.getJSONArray(FLICKR_ITEMS);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonPhoto = jsonArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String author_id = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String tags = jsonPhoto.getString(FLICKR_TAGS);
                String media = jsonPhoto.getString(FLICKR_MEDIA);
                String link = jsonPhoto.getString(FLICKR_LINK);
                JSONObject jsonMedia = new JSONObject(media);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photo = new Photo(title, author, author_id, link, tags, photoUrl);
                photos.add(photo);
            }

            // TODO: Delete succeeding codes after confirmation it works.
            for(Photo sPhoto : photos){
                Log.v(LOG_TAG, sPhoto.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error processing json data");
        }

    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }
}
