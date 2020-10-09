package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class videoplayer extends AppCompatActivity {
    String url1;
    int i = 0;
    ArrayList<String> videolist = new ArrayList<String>();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        final VideoView videoView = findViewById(R.id.video_view);
//            String url = "http://192.168.43.162:8080/Fri%20Oct%2002%202020-Fri%20Oct%2002%202020-Fri%20Oct%2002%202020-Thu%20Oct%2001%202020-Wed%20Sep%2030%202020-sampleVideo.mp4";
        String url = getIntent().getStringExtra("videourl");
        videolist = getIntent().getStringArrayListExtra("videourllist");
//         url="https://shelp-webapp.herokuapp.com/"+url;
//        final ArrayList<String> url = getIntent().getStringArrayListExtra("videourl");
//        final String[] url = getIntent().getStringArrayExtra("videourl");
//        url="http://192.168.43.162:8080/"+url;
//        for (int j = 0; j < url.length; j++) {
//            String url1 = "http://192.168.43.162:8080/Fri%20Oct%2002%202020-Fri%20Oct%2002%202020-Fri%20Oct%2002%202020-Thu%20Oct%2001%202020-Wed%20Sep%2030%202020-sampleVideo.mp4";
//        }
//        while (i < url.size()) {
//            url1 = url.get(i);
//            Uri uri = Uri.parse(url1);
//            videoView.setVideoURI(uri);
//            MediaController mediaController = new MediaController(this);
//            videoView.setMediaController(mediaController);
//            mediaController.setAnchorView(videoView);
//            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
////                    String url2 = "http://192.168.43.162:8080/Dubstep%20Bird%20(Original%205%20Sec%20Video).mp4";
//                    String url2= url.get(i++);
//                    Uri uri = Uri.parse(url2);
//                    videoView.setVideoURI(uri);
//                }
//            });
//        }
        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        Uri uri = Uri.parse(videolist.get(0));
        videoView.start();
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        pd.dismiss();
        videoView.stopPlayback();
        if(i==videolist.size())
            finish();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (i < videolist.size()) {
                    i++;
                    Uri uri = Uri.parse(videolist.get(i));
                    videoView.start();
                    videoView.setVideoURI(uri);
                } else {
                    videoView.stopPlayback();
                    finish();
                }
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                finish();
                return false;
            }
        });
    }
}