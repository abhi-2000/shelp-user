package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.apipackage.retroclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetail extends AppCompatActivity {

    TextView name, tutor, rate, requirement, whatlearn, description, shortdesc;
    RatingBar starbar;
    String course_name, course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        course_id = getIntent().getStringExtra("id");
        course_name = getIntent().getStringExtra("course_name");
        name = findViewById(R.id.textView9);
        tutor = findViewById(R.id.creator);
        rate = findViewById(R.id.rating);
        starbar = findViewById(R.id.ratingBar);
        requirement = findViewById(R.id.requiremnets);
        whatlearn = findViewById(R.id.whatyouwilllearn);
        description = findViewById(R.id.description);
        shortdesc = findViewById(R.id.shoetdescription);
        Call<ResponseBody> call = retroclient
                .getInstance()
                .getapi()
                .detail(course_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();

                } else {
                    try {
                        String str = response.body().string();
                        JSONObject object = new JSONObject(str);
                        for (int i = 0; i < 1; i++) {
                            JSONObject c = object.getJSONObject("course");
                            String imageurl = "http://localhost:8080/Sat%20Oct%2003%202020-React.jpg";
//                            String image = c.getString("imageurl");
//                            image="http://localhost:8080/"+image;
                            JSONObject c1=c.getJSONObject("rating");
                            float star = (float) c1.getDouble("rating");
                            String coursecreator = c.getString("name");
                            String coursetitle = c.getString("title");
                            String courseshortdesc = c.getString("discription");
                            String courselongdesc = c.getString("discriptionLong");
                            String courserequire = c.getString("requirement");
                            String courselearn = c.getString("willLearn");

//                            String id = c.getString("_id");
                            starbar.setRating(star);
                            String starrate = Float.toString(star);
                            name.setText(coursetitle);
                            tutor.setText(coursecreator);
                            rate.setText(starrate);
                            requirement.setText(courserequire);
                            whatlearn.setText(courselearn);
                            description.setText(courselongdesc);
                            shortdesc.setText(courseshortdesc);

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
