package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.Response.Bookmark;
import com.example.retrofit.R;
import com.example.retrofit.apipackage.retroclient;
import com.example.retrofit.videoplayer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetail extends AppCompatActivity {

    TextView name, tutor, rate, requirement, whatlearn, description, shortdesc;
    RatingBar starbar;
    String course_name, course_id;
    ImageView playvideos;
    ImageView topimg, download;
    String userid;
    ImageView bookmark, unbookmark;
    //    String[] videourl = {};
    ArrayList<String> videourllist = new ArrayList<String>();
    String head;
    ////////////
//    private static final int MY_PERSMISSIONS_REQUEST = 100;
    private String[] videourl = {};
    private ProgressDialog progressDialog;
    private WebView mWebview;
    private String[] course_iddetail = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        course_id = getIntent().getStringExtra("courseID");
//        course_name = getIntent().getStringExtra("course_name");
        name = findViewById(R.id.textView9);
        download = findViewById(R.id.imageView3Contact);
        bookmark = findViewById(R.id.bookmark);
        unbookmark = findViewById(R.id.unbookmark);
        tutor = findViewById(R.id.creator);
        rate = findViewById(R.id.rating);
        topimg = findViewById(R.id.topcourseimg);
        starbar = findViewById(R.id.ratingBar);
        playvideos = findViewById(R.id.playvideos);
        requirement = findViewById(R.id.requiremnets);
        whatlearn = findViewById(R.id.whatyouwilllearn);
        description = findViewById(R.id.description);
        progressDialog = new ProgressDialog(this);
        unbookmark.setVisibility(View.GONE);
        bookmark.setVisibility(View.GONE);
//        download = findViewById(R.id.imageView2download);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("userId", 0);
        userid = preferences.getString("userId", null);
        SharedPreferences preference = getApplicationContext().getSharedPreferences("Token", 0);
        head = "Bearer " + preference.getString("Token", null);
//        if (ContextCompat.checkSelfPermission(CourseDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(CourseDetail.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERSMISSIONS_REQUEST);
//        }
//        download.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                         downloadfile();
//
//                                        }
//                                    });


//        download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                Uri uri = Uri.parse("https://cdn.jpegmini.com/user/images/slider_puffin_before_mobile.jpg");
//
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                request.setTitle("My File");
//                request.setDescription("Downloading");
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setVisibleInDownloadsUi(false);
//
//                downloadmanager.enqueue(request);
//            }
//        });

///////////////////////////////////////////////////////////////

//        if (ContextCompat.checkSelfPermission(CourseDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(CourseDetail.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERSMISSIONS_REQUEST);
//        }
//        download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Call<ResponseBody> call1 = retroclient.getInstance().getapi().download(userid,head);
//                  call1.enqueue(new Callback<ResponseBody>() {
//                      @Override
//                      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                          if(!response.isSuccessful())
//                          Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_LONG).show();
//                          else
//                              Toast.makeText(getApplicationContext(),"download",Toast.LENGTH_LONG).show();
//
//                      }
//
//                      @Override
//                      public void onFailure(Call<ResponseBody> call, Throwable t) {
//                          Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//
//                      }
//                  });
//            }
//
//        });


        Call<ResponseBody> call1 = retroclient
                .getInstance()
                .getapi()
                .bookcourse1(userid, head);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                else {
                    try {
                        String st = response.body().string();
                        JSONObject obj = new JSONObject(st);
                        JSONObject obj1 = obj.getJSONObject("course");
                        JSONArray jsonArray = obj1.getJSONArray("bookmarked");
//                        nobookmark.setVisibility(View.GONE);
                        if(jsonArray.length()==0)
                            bookmark.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
//                            String imageurl = c.getString("imageurl");
//                            imageurl = "https://shelp-webapp.herokuapp.com/" + imageurl;
//                            JSONObject rate = c.getJSONObject("rating");
//                            float star = (float) rate.getDouble("ratingFinal");
//                            String name = c.getString("name");
//                            String title = c.getString("title");
                            String id = c.getString("_id");
//                            course_name = Arrays.copyOf(course_name, course_name.length + 1);
//                            course_name[course_name.length - 1] = name;
//                            Toast.makeText(getApplicationContext()," not Book",Toast.LENGTH_LONG).show();

                            if(course_id.equals(id))
                            {
                                unbookmark.setVisibility(View.VISIBLE);
                                bookmark.setVisibility(View.GONE);
                                break;

                            }
                            else
                            {
                                unbookmark.setVisibility(View.GONE);
                                bookmark.setVisibility(View.VISIBLE);
                            }
//                            course_iddetail = Arrays.copyOf(course_iddetail, course_iddetail.length + 1);
//                            course_iddetail[course_iddetail.length - 1] = id;
//                            Toast.makeText(getApplicationContext(),course_iddetail[i]+course_id,Toast.LENGTH_LONG).show();

                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        boolean alreadyBook = false;
//        for (String s : course_iddetail) {
//            if (course_id == s) {
//                alreadyBook = true;
//                break;
//            }
//        }
//        if (alreadyBook) {
//            bookmark.setVisibility(View.GONE);
//            unbookmark.setVisibility(View.VISIBLE);
//        } else {
//            bookmark.setVisibility(View.VISIBLE);
//            unbookmark.setVisibility(View.GONE);
//
//        }

//        //////////////////////////////////////////////////////////////////
        shortdesc = findViewById(R.id.shoetdescription);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<ResponseBody> call = retroclient
                .getInstance()
                .getapi()
                .detail(course_id, head);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        String str = response.body().string();
                        JSONObject object = new JSONObject(str);
                        for (int i = 0; i < 1; i++) {
                            JSONObject c = object.getJSONObject("course");
                            JSONObject c1 = c.getJSONObject("rating");
                            float star = (float) c1.getDouble("ratingFinal");
                            JSONArray video = c.getJSONArray("videourl");
                            if (video.length() != 0) {
                                for (int j = 0; j < video.length(); j++) {
                                    videourl = Arrays.copyOf(videourl, videourl.length + 1);
                                    videourl[videourl.length - 1] = "https://shelp-webapp.herokuapp.com/" + video.getString(j);
//                                videourllist.add(video.getString(j));
                                }
                            }

                            // Iterate through the array
                            for (int j = 0; j < videourl.length; j++) {
                                // Add each element into the list
                                videourllist.add(videourl[j]);
                            }
//                            String imageurl = "http://192.168.43.162/Sat%20Oct%2003%202020-React.jpg";
                            String image = c.getString("imageurl");
                            image = "https://shelp-webapp.herokuapp.com/" + image;

                            String coursecreator = c.getString("name");
                            String coursetitle = c.getString("title");
//                            videourl=c.getString("videourl");
                            String courseshortdesc = c.getString("discription");
                            String courselongdesc = c.getString("discriptionLong");
                            String courserequire = c.getString("requirement");
                            String courselearn = c.getString("willLearn");

//                            String id = c.getString("_id");
                            starbar.setRating(star);
                            String starrate = Float.toString(star);
                            name.setText(coursetitle);
                            Picasso.get().load(image).error(R.drawable.shelplogo).into(topimg);
                            tutor.setText(coursecreator);
                            rate.setText(starrate);
                            requirement.setText(courserequire);
                            whatlearn.setText(courselearn);
                            description.setText(courselongdesc);
                            shortdesc.setText(courseshortdesc);

                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        playvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videourl.length != 0) {
                    Intent i = new Intent(CourseDetail.this, videoplayer.class);
//                Toast.makeText(getApplicationContext(), videourllist.size(), Toast.LENGTH_LONG).show();
//        i.putExtra("videourl", videourllist.get(0));
                    i.putStringArrayListExtra("videourllist", videourllist);
                    i.putExtra("videourl", videourl[0]);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "No videos added", Toast.LENGTH_LONG).show();
                }
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = retroclient
                        .getInstance()
                        .getapi()
                        .download(course_id, head);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            try {

                                Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String url = "https://shelp-webapp.herokuapp.com/invoice-" + course_id + ".pdf";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });


    }

    private void downloadfile() {

    }

    public void contact(View view) {
        final Intent intent = new Intent(Intent.ACTION_VIEW)
                .setType("plain/text")
                .setData(Uri.parse("cookbook@gmail.com"))
                .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        intent.putExtra(Intent.EXTRA_TEXT, "Feedback Message");
        intent.putExtra(Intent.EXTRA_EMAIL, "cookbook@gmail.com");
        startActivity(Intent.createChooser(intent, ""));

    }

    public void playvideo(View view) {
//        Intent i = new Intent(CourseDetail.this, videoplayer.class);
//        Toast.makeText(getApplicationContext(), videourl.length, Toast.LENGTH_LONG).show();
//        i.putExtra("videourl", videourl);
//        startActivity(i);

    }

    public void displayvideos(View view) {
//        Intent i = new Intent(this, VideoList.class);
    }

    public void book(View view) {
        Bookmark mark = new Bookmark(course_id, userid);
        Call<ResponseBody> call = retroclient.
                getInstance().
                getapi()
                .bookmark(mark, head);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Course bookmarked", Toast.LENGTH_LONG).show();
                    bookmark.setVisibility(View.GONE);
                    unbookmark.setVisibility(View.VISIBLE);
                } else
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void unbook(View view) {
        Bookmark mark = new Bookmark(course_id, userid);
        Call<ResponseBody> call = retroclient.
                getInstance().
                getapi()
                .unbookmark(mark);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Course unbookmarked", Toast.LENGTH_LONG).show();
                    bookmark.setVisibility(View.VISIBLE);
                    unbookmark.setVisibility(View.GONE);
                } else
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    private boolean writeResponseBodyToDisk(ResponseBody body) {
//        try {
//            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.pdf");
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                byte[] fileReader = new byte[4096];
//
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
//
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(futureStudioIconFile);
//
//                while (true) {
//                    int read = inputStream.read(fileReader);
//
//                    if (read == -1) {
//                        break;
//                    }
//
//                    outputStream.write(fileReader, 0, read);
//
//                    fileSizeDownloaded += read;
//
//                }
//
//                outputStream.flush();
//
//                return true;
//            } catch (IOException e) {
//                return false;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            }
//        } catch (IOException e) {
//            return false;
//        }
//    }
}