package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.Reotp;
import com.example.retrofit.Response.OtpResponse;
import com.example.retrofit.Response.ResendResponse;
import com.example.retrofit.Sharedprefs;
import com.example.retrofit.Verify;
import com.example.retrofit.apipackage.retroclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp extends AppCompatActivity implements View.OnClickListener {
    EditText et_otp;
    String token,email;
    TextView resend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        resend=findViewById(R.id.tvresend_otp);
        Intent intent= getIntent();
        token = intent.getStringExtra("Token");
        email=intent.getStringExtra("email");
        et_otp= findViewById(R.id.etotp);
        findViewById(R.id.btn_confirmemail).setOnClickListener(this);
        findViewById(R.id.tvresend_otp).setOnClickListener(this);
        findViewById(R.id.tv_login1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_confirmemail:
                verify();
                break;

            case R.id.tvresend_otp:
                resend.setEnabled(false);

                resend.postDelayed(new Runnable() {
                                       public void run() {
                                           resend.setEnabled(true);
                                       }
                                   }, 300 * 100);
                resendotp();

                break;

            case R.id.tv_login1:
                Intent intent=new Intent(this, loginActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }

    private void resendotp() {
    Reotp reotp=new Reotp(token,email);
    Call<ResendResponse> call1= retroclient
            .getInstance()
            .getapi()
            .resend(reotp);
    call1.enqueue(new Callback<ResendResponse>() {
        @Override
        public void onResponse(Call<ResendResponse> call1, Response<ResendResponse> response) {
            if (!response.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "response:" + response.message(), Toast.LENGTH_LONG).show();
            } else {
                assert response.body() != null;
                Toast.makeText(getApplicationContext(), "New OTP is send to your mail", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFailure(Call<ResendResponse> call1, Throwable t) {
            Toast.makeText(getApplicationContext(),"failure:"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
    });
    }

    private void verify() {
      String otp=et_otp.getText().toString();
        if(otp.isEmpty())
        {
            et_otp.setError("OTP is required");
            et_otp.requestFocus();
            return;
        }
        else
        {
            Verify verify= new Verify(token,otp);
            Call<OtpResponse> call= retroclient
                    .getInstance()
                    .getapi()
                    .verifymail(verify);
             call.enqueue(new Callback<OtpResponse>() {
                 @Override
                 public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                     try {
                         if (response.isSuccessful()) {
                             OtpResponse res = response.body();
                             Toast.makeText(otp.this, res.getMessage(), Toast.LENGTH_LONG).show();
                             Sharedprefs.saveSharedsetting(otp.this,"Clip" ,"false");
                             Sharedprefs.sharedprefsave(getApplicationContext(), res.getUsername(),res.getToken(),res.getUserId());
                             Intent isverified = new Intent(getApplicationContext(), student.class);
                             startActivity(isverified);
                             finish();

                         } else {
                             String s = response.errorBody().string();
                             JSONObject jsonObject = new JSONObject(s);
                             String message = jsonObject.getString("message");
                             Toast.makeText(otp.this, message, Toast.LENGTH_LONG).show();
                         }
                     } catch (IOException | JSONException e) {
                         e.printStackTrace();
                     }
                 }

                 @Override
                 public void onFailure(Call<OtpResponse> call, Throwable t) {
                         Toast.makeText(otp.this, t.getMessage(),Toast.LENGTH_LONG).show();

                 }
             });

        }
    }
}