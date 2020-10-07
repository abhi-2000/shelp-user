package com.example.retrofit.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.retrofit.Adapter.SecondAdapter;
import com.example.retrofit.Adapter.firstAdapter;
import com.example.retrofit.ModelClass.SecondModelClass;
import com.example.retrofit.ModelClass.firstmodelclass;
import com.example.retrofit.ParticularCategoryActivity;
import com.example.retrofit.R;
import com.example.retrofit.Sharedprefs;
import com.example.retrofit.apipackage.retroclient;
import com.google.android.material.navigation.NavigationView;

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

public class student extends AppCompatActivity implements firstAdapter.OnItemClickListener {
    RecyclerView firstrv, secondrv;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    List<firstmodelclass> modleClassList = new ArrayList<>();
    List<SecondModelClass> secondModelClassList = new ArrayList<>();
    private String[] course_id = {};
    private String[] course_name = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        checkuser();
        firstrv = findViewById(R.id.firstrv);
        secondrv = findViewById(R.id.secondrv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = (NavigationView) findViewById(R.id.navbar);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.web:
//                        final Intent intent = new Intent(Intent.ACTION_VIEW)
//                                .setType("plain/text")
//                                .setData(Uri.parse("cookbook@gmail.com"))
//                                .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
//                        intent.putExtra(Intent.EXTRA_TEXT, "Feedback Message");
//                        intent.putExtra(Intent.EXTRA_EMAIL, "cookbook@gmail.com");
//                        startActivity(Intent.createChooser(intent, ""));
//                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.android:
                        Toast.makeText(getApplicationContext(), "Android", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(student.this, ParticularCategoryActivity.class);
                        intent.putExtra("cat","ML");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        break;


                    case R.id.graphic:
                        break;

                    case R.id.react:
                        break;
                    case R.id.node:
                        break;


                    case R.id.photography:
                        break;
                }
                return true;
            }
        });
//        adapter = new firstAdapter(modleClassList);
//        firstrv.setAdapter(adapter);
//        adapter.setOnItemClickListener(student.this);
//        adapter.notifyDataSetChanged();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(student.this, LinearLayoutManager.HORIZONTAL, false);
//        firstrv.setLayoutManager(linearLayoutManager);

        Call<ResponseBody> call = retroclient
                .getInstance()
                .getapi()
                .home("all");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();

                } else {
                    try {
                        trendingrv();
                        String str = response.body().string();
                        JSONObject object = new JSONObject(str);
                        JSONArray jsonArray = object.getJSONArray("course");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject c = jsonArray.getJSONObject(i);
//                            String imageurl = "http://localhost:8080/Sat%20Oct%2003%202020-React.jpg";
                            String imageurl = c.getString("imageurl");
                            imageurl = "http://192.168.43.162:8080/" + imageurl;
//                            JSONObject c1=c.getJSONObject("rating");
                            JSONObject rate = c.getJSONObject("rating");
                            float star = (float) rate.getDouble("ratingFinal");

//                            String star=rate.getString("ratingFinal");
                            String name = c.getString("name");
                            String title = c.getString("title");
                            String id = c.getString("_id");
                            course_name = Arrays.copyOf(course_name, course_name.length + 1);
                            course_name[course_name.length - 1] = name;
                            course_id = Arrays.copyOf(course_id, course_id.length + 1);
                            course_id[course_id.length - 1] = id;
                            modleClassList.add(new firstmodelclass(imageurl, title, name, star));
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(student.this, LinearLayoutManager.HORIZONTAL, false);
                            firstrv.setLayoutManager(linearLayoutManager);
                            firstAdapter adapter = new firstAdapter(modleClassList);
                            firstrv.setAdapter(adapter);
                            adapter.setOnItemClickListener(student.this);
                            adapter.notifyDataSetChanged();
                        }

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


    }

    private void trendingrv() {
        Call<ResponseBody> call1 = retroclient
                .getInstance()
                .getapi()
                .home("all");
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();

                } else {
                    try {
                        String str = response.body().string();
                        JSONObject object = new JSONObject(str);
                        JSONArray jsonArray = object.getJSONArray("course");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (i % 2 == 0) {
                                JSONObject c = jsonArray.getJSONObject(i);
//                            String imageurl = "http://localhost:8080/Sat%20Oct%2003%202020-React.jpg";
//                            String image = c.getString("imageurl");
//                            image="http://localhost:8080/"+image;
                                String imageurl = c.getString("imageurl");
                                imageurl = "http://192.168.43.162:8080/" + imageurl;

                                String name = c.getString("name");
                                String title = c.getString("title");
                                JSONObject rate = c.getJSONObject("rating");
                                float star = (float) rate.getDouble("ratingFinal");
                                secondModelClassList.add(new SecondModelClass(imageurl, title, name, star));
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(student.this, LinearLayoutManager.VERTICAL, false);
                                secondrv.setLayoutManager(linearLayoutManager);
                                SecondAdapter adapter1 = new SecondAdapter(secondModelClassList);
                                secondrv.setAdapter(adapter1);
                                adapter1.notifyDataSetChanged();
                            }
                        }
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


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(student.this, CourseDetail.class);
        String id = course_id[position];
        String name = course_name[position];
        intent.putExtra("courseID", id);
//        intent.putExtra("userID",userid);
        intent.putExtra("course_name", name);
        Toast.makeText(student.this, course_id[position].toString(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
public void checkuser()
{
    Boolean check= Boolean.valueOf(Sharedprefs.readShared(student.this,"Clip","true"));

    Intent intro = new Intent(student.this, SignupActivity.class);
    intro.putExtra("Clip",check);

    if(check){
        finish();
        startActivity(intro);
    }
}
}