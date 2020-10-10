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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.Adapter.SecondAdapter;
import com.example.retrofit.Adapter.firstAdapter;
import com.example.retrofit.ModelClass.SecondModelClass;
import com.example.retrofit.ModelClass.firstmodelclass;
import com.example.retrofit.R;
import com.example.retrofit.Sharedprefs;
import com.example.retrofit.apipackage.retroclient;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
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

public class student extends AppCompatActivity implements firstAdapter.OnItemClickListener, SecondAdapter.OnItemClickListener {
    RecyclerView firstrv, secondrv;
    NavigationView nav;
    TextView emailtxtview, nametxtview;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    List<firstmodelclass> modleClassList = new ArrayList<>();
    List<SecondModelClass> secondModelClassList = new ArrayList<>();
    private String[] course_id = {};
    private String[] course_idtrend = {};
    private String[] course_name = {};
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
//        checkuser();
        nametxtview = findViewById(R.id.namenav);
        firstrv = findViewById(R.id.firstrv);
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("email", 0);
//        final String email = preferences.getString("email", null);
//        emailtxtview.setText(email);
//        SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("Name", 0);
//        final String name = preferences1.getString("Name", null);
//        nametxtview.setText(name);
        secondrv = findViewById(R.id.secondrv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        nav = (NavigationView) findViewById(R.id.navbar);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homenav:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.bookmarknav:
                        Intent intent2 = new Intent(student.this, BookmarkActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent2);
                        break;

                    case R.id.web:
                        Intent intent = new Intent(student.this, ParticularCategoryActivity.class);
                        intent.putExtra("cat", "Web Development");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        break;

                    case R.id.ml:
                        Intent intent1 = new Intent(student.this, ParticularCategoryActivity.class);
                        intent1.putExtra("cat", "Machine Learning");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent1);
                        break;


                    case R.id.graphic:
                        Intent intent3 = new Intent(student.this, ParticularCategoryActivity.class);
                        intent3.putExtra("cat", "Designing");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent3);
                        break;

                    case R.id.react:
                        Intent intent4 = new Intent(student.this, ParticularCategoryActivity.class);
                        intent4.putExtra("cat", "React");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent4);

                        break;
                    case R.id.node:
                        Intent intent5 = new Intent(student.this, ParticularCategoryActivity.class);
                        intent5.putExtra("cat", "Nodejs");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent5);

                        break;


                    case R.id.photography:
                        Intent intent6 = new Intent(student.this, ParticularCategoryActivity.class);
                        intent6.putExtra("cat", "Photography");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent6);

                        break;
                    case R.id.logout:
                        new iOSDialogBuilder(student.this)
                                .setTitle("Logout")
                                .setSubtitle("Are you sure?")
                                .setCancelable(false)
                                .setPositiveListener(getString(R.string.ok), new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        Sharedprefs.saveSharedsetting(getApplicationContext(), "Clip", "true");
                                        Sharedprefs.sharedprefsave(getApplicationContext(), "", "", "");
                                        Intent signout = new Intent(getApplicationContext(), loginActivity.class);
                                        finish();
                                        startActivity(signout);
                                    }
                                })
                                .setNegativeListener(getString(R.string.dismiss), new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .build().show();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
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
        progressDialog.setMessage("Loading");
        progressDialog.show();
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

    private void dialogboxfun() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(student.this);
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

    private void retrowork() {
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
//                            String imageurl = "http://192.168.43.162:8080/Sat%20Oct%2003%202020-React.jpg";
                            String imageurl = c.getString("imageurl");
                            imageurl = "https://shelp-webapp.herokuapp.com/" + imageurl;
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
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

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
                                imageurl = "https://shelp-webapp.herokuapp.com/" + imageurl;
                                String id = c.getString("_id");
                                course_idtrend = Arrays.copyOf(course_idtrend, course_idtrend.length + 1);
                                course_idtrend[course_idtrend.length - 1] = id;

                                String name = c.getString("name");
                                String title = c.getString("title");
                                JSONObject rate = c.getJSONObject("rating");
                                float star = (float) rate.getDouble("ratingFinal");
                                secondModelClassList.add(new SecondModelClass(imageurl, title, name, star));
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(student.this, LinearLayoutManager.VERTICAL, false);
                                secondrv.setLayoutManager(linearLayoutManager);
                                SecondAdapter adapter1 = new SecondAdapter(secondModelClassList);
                                secondrv.setAdapter(adapter1);
                                adapter1.setOnItemClickListener(student.this);
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
//        String name = course_name[position];
        intent.putExtra("courseID", id);
//        intent.putExtra("userID",userid);
//        intent.putExtra("course_name", name);
//        Toast.makeText(student.this, course_id[position].toString(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, CourseDetail.class);
        intent.putExtra("courseID", course_idtrend[position]);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        ShowDiag();
    }

    private void ShowDiag() {
//        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "Gilroy-ExtraBold.ttf");
        new iOSDialogBuilder(this)
                .setTitle("Exit")
                .setSubtitle("Ohh no! You're leaving...\nAre you sure?")
                .setCancelable(false)
                .setPositiveListener(getString(R.string.ok), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                        CloseApp();
                    }
                })
                .setNegativeListener(getString(R.string.dismiss), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

    private void CloseApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }
//    public void checkuser() {
//        Boolean check = Boolean.valueOf(Sharedprefs.readShared(student.this, "Clip", "true"));
//
//        Intent intro = new Intent(student.this, SignupActivity.class);
//        intro.putExtra("Clip", check);
//
//        if (check) {
//            finish();
//            startActivity(intro);
//        }
//    }
}