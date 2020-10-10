package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.ModelClass.ResetPass;
import com.example.retrofit.apipackage.retroclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class resetPassword extends AppCompatActivity {

    EditText pass1, pass2;
    String p1, p2, email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_new_pass);
        pass1 = findViewById(R.id.etpassword);
        pass2 = findViewById(R.id.etconfirmpassword);
        reset = findViewById(R.id.btnreset);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1 = pass1.getText().toString();
                p2 = pass2.getText().toString();
                if (p1.isEmpty()) {
                    pass1.setError("Password is required");
                    pass1.requestFocus();
                    return;
                }
                if (p1.length() < 5) {
                    pass1.setError("Password must be atleast 5 characters long");
                    pass1.requestFocus();
                    return;
                }

                if (!p2.equals(p1)) {
                    pass2.setError("Passwords do not match");
                    pass2.requestFocus();
                    return;
                } else {
                    ResetPass reset = new ResetPass(email, p1, p2);
                    Call<ResponseBody> call = retroclient
                            .getInstance()
                            .getapi()
                            .resetpass(reset);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (!response.isSuccessful()) {
                                assert response.errorBody() != null;
                                Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    String st = response.body().string();
                                    JSONObject obj = new JSONObject(st);
                                    String msg = obj.getString("messsage");
                                    Toast.makeText(resetPassword.this, msg, Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(resetPassword.this, SignupActivity.class);
                                    finish();
                                    startActivity(i);
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
            }
        });
    }
}
