package com.example.abetrosita.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AbetRosita on 12/26/2016.
 */

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

public class RawData {

    private String LOG_TAG = RawData.class.getSimpleName();
    private String rawUrl;
    private String urlData;
    private DownloadStatus downloadStatus;

    public RawData(String rawUrl) {
        this.rawUrl = rawUrl;
        this.downloadStatus = DownloadStatus.IDLE;
    }

    public void reset(){
        this.downloadStatus = DownloadStatus.IDLE;
        this.rawUrl = null;
        this.urlData = null;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public String getUrlData() {
        return urlData;
    }

    public void execute(){
        this.downloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(rawUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);

            urlData = webData;
//            Log.v(LOG_TAG, "Data returned is: " + this.urlData);
            if(urlData == null){
                if(rawUrl == null){
                    downloadStatus = DownloadStatus.NOT_INITIALIZED;
                }else {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }else {
                downloadStatus = DownloadStatus.OK;
                Log.v(LOG_TAG, "Photo data successfully downloaded.");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if(params == null){
                return null;
            }

            try{
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                if(inputStream == null){
                    return null;
                }

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            }catch (IOException e){
                e.printStackTrace();
                Log.e(LOG_TAG, "Error", e);
            } finally {
                if(urlConnection == null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return null;
        }
    }


}
