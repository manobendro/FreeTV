package com.biswas.freetv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;


public class Streaming extends ActionBarActivity {
    Intent iIntent;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);
        pDialog=ProgressDialog.show(this,"","Preparing...",true);
        pDialog.setCancelable(true);

        iIntent=getIntent();
        String url=iIntent.getStringExtra(MainActivity.intentID);
        Log.d(MainActivity.logTag,url);
        Uri video=Uri.parse(url);
        final VideoView vView= (VideoView) findViewById(R.id.videoView);
        try {
            android.widget.MediaController mController=new android.widget.MediaController(getBaseContext());
            mController.setAnchorView(vView);
            vView.setMediaController(mController);
            vView.setVideoURI(video);
            vView.requestFocus();
            vView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    pDialog.dismiss();
                    vView.start();
                }
            });
        } catch (Exception e) {
            pDialog.dismiss();
            Toast.makeText(getBaseContext(),"Something rong.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}
