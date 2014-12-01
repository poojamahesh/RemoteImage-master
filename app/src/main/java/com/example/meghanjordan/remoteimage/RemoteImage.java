package com.example.meghanjordan.remoteimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by meghanjordan on 10/15/14.
 */
public class RemoteImage extends AsyncTask<String, Integer, Bitmap> {


    private final int OK = 200;

    private Activity activity;

    public RemoteImage(Activity myActivity) {
        activity = myActivity;
    }


    @Override

    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        publishProgress(1);
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            if (httpCon.getResponseCode() != OK)
                throw new Exception("Failed to connect");

            InputStream is = httpCon.getInputStream();
            publishProgress(0);
            return BitmapFactory.decodeStream(is);
			if (isCancelled()) break;

        } catch (Exception e) {
            Log.e("Image", "Failed to load image", e);
            Log.e("", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        TextView tv = (TextView) activity.findViewById(R.id.loading);
		setProgressPercent(values[0]);
        if (values[0] == 1) {
            tv.setText("Loading...");
            ProgressBar pb = (ProgressBar)activity.findViewById(R.id.progressBar);
        } else {
            tv.setText("");
        }

    }

    @Override
    protected void onPostExecute(Bitmap img) {
        ImageView iv = (ImageView) activity.findViewById(R.id.remote_image);
        if (iv != null && img != null) {
            iv.setImageBitmap(img);
        }

    }


}
