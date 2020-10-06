package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.retrofit.Adapter.firstAdapter;
import com.example.retrofit.ModelClass.firstmodelclass;
import com.example.retrofit.UI.student;
import com.example.retrofit.apipackage.retroclient;

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

public class ParticularCategoryActivity extends AppCompatActivity {

    private String[] course_name = {};
    private String[] course_id = {};
    List<firstmodelclass> modleClassList = new ArrayList<>();
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_course);
        rcv = findViewById(R.id.recyclerview);

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
                        String str = response.body().string();
                        JSONObject object = new JSONObject(str);
                        JSONArray jsonArray = object.getJSONArray("course");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            String imageurl = "http://localhost:8080/Sat%20Oct%2003%202020-React.jpg";
//                            String image = c.getString("imageurl");
//                            image="http://localhost:8080/"+image;
                            JSONObject c1 = c.getJSONObject("rating");
                            float star = (float) c1.getDouble("rating");
                            String name = c.getString("name");
                            String title = c.getString("title");
                            String id = c.getString("_id");
                            course_name = Arrays.copyOf(course_name, course_name.length + 1);
                            course_name[course_name.length - 1] = name;
                            course_id = Arrays.copyOf(course_id, course_id.length + 1);
                            course_id[course_id.length - 1] = id;
//                            modleClassList.add(new firstmodelclass(imageurl, title, name, star));
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ParticularCategoryActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            rcv.setLayoutManager(linearLayoutManager);
                            firstAdapter adapter = new firstAdapter(modleClassList);
                            rcv.setAdapter(adapter);
                            adapter.setOnItemClickListener((firstAdapter.OnItemClickListener) ParticularCategoryActivity.this);
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

}
