package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.retrofit.Adapter.SecondAdapter;
import com.example.retrofit.Adapter.firstAdapter;
import com.example.retrofit.ModelClass.SecondModelClass;
import com.example.retrofit.ModelClass.firstmodelclass;
import com.example.retrofit.UI.CourseDetail;
import com.example.retrofit.UI.student;
import com.example.retrofit.apipackage.retroclient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity implements SecondAdapter.OnItemClickListener {


    private String[] course_idbook = {};
    private List<SecondModelClass> secondModelClassList = new ArrayList<>();
    private RecyclerView bookrv;
    LinearLayout layoutlinear;
    ImageView nobookmark;
    String userID,head;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("userId", 0);
        userID = preferences.getString("userId", null);
        SharedPreferences preference = getApplicationContext().getSharedPreferences("Token", 0);
         head = "Bearer " + preference.getString("Token", null);
        bookrv = findViewById(R.id.rcvbookmark);
        layoutlinear=findViewById(R.id.layoutlinear);
        nobookmark=findViewById(R.id.nobookmark);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.show();
        CheckInternet();

    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, CourseDetail.class);
        intent.putExtra("courseID", course_idbook[position]);
        startActivity(intent);

    }
    private void CheckInternet() {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext() .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if(null!=activeNetwork){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE || activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
               bookmarkfunc();
            }
        }
        else{
            dialogboxfun();

        }
    }

    private void bookmarkfunc() {
        Call<ResponseBody> call = retroclient
                .getInstance()
                .getapi()
                .bookcourse(userID, head);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                else {
                    try {
                        String st = response.body().string();
                        JSONObject obj = new JSONObject(st);
                        JSONObject obj1 = obj.getJSONObject("course");
                        JSONArray jsonArray = obj1.getJSONArray("bookmarked");
                        layoutlinear.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            layoutlinear.setVisibility(View.GONE);
                            bookrv.setVisibility(View.VISIBLE);
                            JSONObject c = jsonArray.getJSONObject(i);
                            String imageurl = c.getString("imageurl");
                            imageurl = "https://shelp-webapp.herokuapp.com/" + imageurl;
                            JSONObject rate = c.getJSONObject("rating");
                            float star = (float) rate.getDouble("ratingFinal");
                            String name = c.getString("name");
                            String title = c.getString("title");
                            String id = c.getString("_id");
//                            course_name = Arrays.copyOf(course_name, course_name.length + 1);
//                            course_name[course_name.length - 1] = name;
                            course_idbook = Arrays.copyOf(course_idbook, course_idbook.length + 1);
                            course_idbook[course_idbook.length - 1] = id;
                            secondModelClassList.add(new SecondModelClass(imageurl, title, name, star));
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookmarkActivity.this, LinearLayoutManager.VERTICAL, false);
                            bookrv.setLayoutManager(linearLayoutManager);
                            SecondAdapter adapter1 = new SecondAdapter(secondModelClassList);
                            bookrv.setAdapter(adapter1);
                            adapter1.setOnItemClickListener((SecondAdapter.OnItemClickListener) BookmarkActivity.this);
                            adapter1.notifyDataSetChanged();
                        }

                        pd.dismiss();
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
    }

    private void dialogboxfun() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(BookmarkActivity.this);
        builder2.setMessage("No internet Connection");
        builder2.setCancelable(false);
        builder2.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CheckInternet();
                    }
                });
        builder2.setNegativeButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });
        AlertDialog alert12 = builder2.create();
        alert12.show();

    }


    public void gohome(View view) {
        finish();

    }

    public void backhome(View view) {
        finish();
    }
}