package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.UI.student;
import com.example.retrofit.apipackage.Checkotp;
import com.example.retrofit.apipackage.retroclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetotpConfirm extends AppCompatActivity {
    String token, email;
    EditText editText;
    String otpentered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetotp_confirm);
        editText = findViewById(R.id.edittxtresetotp);
        token = getIntent().getStringExtra("token");
        email = getIntent().getStringExtra("email");
        findViewById(R.id.btn_confirrest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpentered = editText.getText().toString();
                if (otpentered.isEmpty()) {
                    editText.setError("Email cannot be empty");
                    editText.requestFocus();
                } else {
                    sent();
                }
            }
        });
    }

    private void sent() {
        Checkotp che = new Checkotp(token, otpentered);
        Call<ResponseBody> call1 = retroclient
                .getInstance()
                .getapi()
                .check(che);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    try {
                        assert response.errorBody() != null;
                        String string = response.errorBody().string();
                        JSONObject jsonObject1 = new JSONObject(string);
                        String wrong = jsonObject1.getString("message");
                        Toast.makeText(getApplicationContext(), wrong, Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.body() != null;
                        String str = response.body().string();
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                        if (str.equalsIgnoreCase("\"Otp verified\"")) {
                            Intent intent = new Intent(ResetotpConfirm.this, resetPassword.class);
                            intent.putExtra("email", email);
                            finish();
                            startActivity(intent);
                        }
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