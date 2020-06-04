package com.example.owa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class helpGuide extends AppCompatActivity {
    Button b1;
    VideoView vv;
    MediaController MC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_guide);

        b1 = findViewById(R.id.enq);
        vv = findViewById(R.id.videoView);
        MC = new MediaController(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{"courseproject22020@gmail.com"});
                it.putExtra(Intent.EXTRA_SUBJECT, "Enqiries");
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it, "Choose Mail App"));
            }
        });


    }

    public void videoplay(View v) {
        String videopath = "android.resource://com.example.owa/" + R.raw.help;
        Uri uri = Uri.parse(videopath);
        vv.setVideoURI(uri);
        vv.setMediaController(MC);
        MC.setAnchorView(vv);
        vv.start();

    }

}
