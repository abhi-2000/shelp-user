package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.retrofit.Response.LoginResponse;
import com.example.retrofit.R;
import com.example.retrofit.Sharedprefs;
import com.example.retrofit.apipackage.RetrofitClient;
import com.example.retrofit.ModelClass.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {
    private EditText email, password;
    Button login;
    String emailtxt = "", passwordtxt = "";
     TextView reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = findViewById(R.id.button);
        email = findViewById(R.id.edittxtEmail);
        password = findViewById(R.id.edittxtPass);
        reset=findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginActivity.this, resetotp.class);
                finish();
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginActivity.this, SignupActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void LoginUser() {
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        if (emailtxt.isEmpty()) {
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()) {
            email.setError("Please enter a valid email id");
            email.requestFocus();
            return;
        }
        if (passwordtxt.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return;
        }
        if (passwordtxt.length() < 6) {
            password.setError("Password should atleast 6 char long");
            password.requestFocus();
            return;
        } else {
            RetroWork();
        }
    }

    private void RetroWork() {
        login log1 = new login(emailtxt, passwordtxt);
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().loginUser(log1);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        String string = response.errorBody().string();
                        JSONObject jsonObject1 = new JSONObject(string);
                        String wrong = jsonObject1.getString("message");
                        Toast.makeText(getApplicationContext(), wrong, Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject(string);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject meals = jsonArray.getJSONObject(0);
                        String wrong1=meals.getString("msg");
                        Toast.makeText(getApplicationContext(), wrong1, Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    String token = response.body().getToken();
                    String userId=response.body().getUserId();
                   // SharedPreferences sharedPreferences = getSharedPreferences("t", MODE_PRIVATE);
                    //SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.putString("str", token);
                    //editor.apply();
                    Sharedprefs.saveSharedsetting(loginActivity.this,"Clip" ,"false");
                    Sharedprefs.sharedprefsave(getApplicationContext(), response.body().getUsername(),token,response.body().getUserId());
                    Intent islogged = new Intent(getApplicationContext(), student.class);
                    startActivity(islogged);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
