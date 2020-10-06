package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.apipackage.retroclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetotpConfirm extends AppCompatActivity {
    String token;
    EditText editText;
    String otpentered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetotp_confirm);
        editText=findViewById(R.id.edittxtresetotp);
        otpentered=editText.getText().toString();
        token = getIntent().getStringExtra("token");
        findViewById(R.id.btn_confirrest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpentered.isEmpty()) {
                    editText.setError("Email cannot be empty");
                    editText.requestFocus();
                    return;
                }
                else {

                    sent();
                }
            }
        });
    }

    private void sent() {
        Call<ResponseBody> call1 = retroclient
                .getInstance()
                .getapi()
                .check(otpentered, token);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                try {
                    String str = response.body().string();
                    JSONObject object = new JSONObject(str);
                    JSONObject obj1 = object.getJSONObject("result");
                    token = obj1.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}