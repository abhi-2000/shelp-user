package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.apipackage.course;
import com.example.retrofit.apipackage.retroclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class resetotp extends AppCompatActivity {

    EditText email_txt;
    String email;
    String token;
    private String res = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetotp);
        email_txt = findViewById(R.id.edittxtEmailreset);
        findViewById(R.id.tv_login1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(resetotp.this, loginActivity.class);
                finish();
                startActivity(i);
            }
        });

        findViewById(R.id.btn_resetotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_txt.getText().toString();
                if (email.isEmpty()) {
                    email_txt.setError("Email cannot be empty");
                    email_txt.requestFocus();
                    return;
                } else {
                    sent();


                }
            }
        });
    }

    private void sent() {
        course cr = new course(email);

        Call<ResponseBody> call1 = retroclient
                .getInstance()
                .getapi()
                .sent(cr);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "wrong", Toast.LENGTH_LONG).show();
                    try {
                        assert response.errorBody() != null;
                        String string = response.errorBody().string();
                        JSONObject jsonObject1 = new JSONObject(string);
                        String wrong = jsonObject1.getString("msg");
                        Toast.makeText(getApplicationContext(), wrong, Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.body() != null;
                        String str = response.body().string();
                        JSONObject object = new JSONObject(str);
                        JSONObject obj2 = object.getJSONObject("result");
                        token = obj2.getString("token");
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(resetotp.this, ResetotpConfirm.class);
                        intent.putExtra("token", token);
                        intent.putExtra("email",email);
                        finish();
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
