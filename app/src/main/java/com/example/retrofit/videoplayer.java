package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.retrofit.UI.student;

import java.util.ArrayList;

public class videoplayer extends AppCompatActivity {
    String url1;
    int i = 0;
    ArrayList<String> videolist = new ArrayList<String>();
    ProgressDialog pd;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        videoView = findViewById(R.id.video_view);
//            String url = "http://192.168.43.162:8080/Fri%20Oct%2002%202020-Fri%20Oct%2002%202020-Fri%20Oct%2002%202020-Thu%20Oct%2001%202020-Wed%20Sep%2030%202020-sampleVideo.mp4";
        String url = getIntent().getStringExtra("videourl");
        videolist = getIntent().getStringArrayListExtra("videourllist");
        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        CheckInternet();
    }
    private void CheckInternet() {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE || activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                retrowork();
            }
        } else {

            dialogboxfun();

        }
    }

    private void retrowork() {
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

    private void dialogboxfun() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(videoplayer.this);
        builder1.setMessage("No internet Connection");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CheckInternet();
                    }
                });
        builder1.setNegativeButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}