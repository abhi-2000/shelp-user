package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.retrofit.Adapter.SecondAdapter;
import com.example.retrofit.Adapter.firstAdapter;
import com.example.retrofit.ModelClass.SecondModelClass;
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
    private List<SecondModelClass> secondModelClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_course);
        rcv = findViewById(R.id.recyclerview);
        String str = getIntent().getStringExtra("cat");
        Call<ResponseBody> call1 = retroclient
                .getInstance()
                .getapi()
                .home(str);
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
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ParticularCategoryActivity.this, LinearLayoutManager.VERTICAL, false);
                            rcv.setLayoutManager(linearLayoutManager);
                            SecondAdapter adapter1 = new SecondAdapter(secondModelClassList);
                            rcv.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();
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
