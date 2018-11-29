package com.example.whgml.videoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private MediaController mediaController;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView)findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

//        videoView.setVideoURI();
//        mediaController.setAnchorView(videov);
//        videov.start();
    }
}
