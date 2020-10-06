package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.apipackage.retroclient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class resetPassword extends AppCompatActivity {

    EditText pass1, pass2;
    String p1, p2;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        pass1 = findViewById(R.id.password1);
        pass2 = findViewById(R.id.password2);
        reset = findViewById(R.id.button);
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

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
                                Toast.makeText(getApplicationContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            }
                            assert response.body() != null;
                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();

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
