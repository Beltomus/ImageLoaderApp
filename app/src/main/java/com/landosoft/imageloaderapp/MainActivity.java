package com.landosoft.imageloaderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String MY_URL_STRING = "http://heartofgreen.typepad.com/.a/6a00d83451cedf69e201a73dcaba0a970d-pi";
    private static final String MY_URL_STRING_2 = "http://images5.fanpop.com/image/photos/27900000/Ocean-Animals-animals-27960311-1920-1200.jpg";

    ImageView mImageView;
    ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(MY_URL_STRING);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
                .execute(MY_URL_STRING_2);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView2 = (ImageView) findViewById(R.id.imageView2);

        setUI();
    }

    private void setUI() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                /*mImageView.buildDrawingCache();
                Bitmap bmp = mImageView.getDrawingCache();
                i.putExtra("BitmapImage", bmp);
                startActivity(i);*/
                Bitmap b1 = null; // your bitmap
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b1.compress(Bitmap.CompressFormat.PNG, 50, bs);
                i.putExtra("byteArray", bs.toByteArray());
                startActivity(i);

            }
        });

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap btmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                btmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return btmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
