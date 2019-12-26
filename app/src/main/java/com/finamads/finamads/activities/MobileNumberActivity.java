package com.finamads.finamads.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.finamads.finamads.R;
import com.finamads.finamads.model.CheckPhoneResponse;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.DrService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileNumberActivity extends AppCompatActivity {

    Button btnMobile;
    DrService apiInterface;
    EditText phoneNumber;
    LinearLayout layoutprograss;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

        btnMobile = findViewById(R.id.submit_number);
        phoneNumber = findViewById(R.id.phone_number);
        layoutprograss = findViewById(R.id.mobile_progress_layout);
        btnMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(MobileNumberActivity.this, "Please enter valid 10 digit phone number!!", Toast.LENGTH_LONG).show();
                }else{

                    if (phoneNumber.getText().length() == 10){
                        layoutprograss.setVisibility(View.VISIBLE);
                        checkPhoneNumber();
                    }else{
                        Toast.makeText(MobileNumberActivity.this, "Please enter valid 10 digit phone number!!", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });
    }

    private void checkPhoneNumber() {

        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<CheckPhoneResponse> call = apiInterface.loginUser(phoneNumber.getText().toString());
        call.enqueue(new Callback<CheckPhoneResponse>() {
            @Override
            public void onResponse(Call<CheckPhoneResponse> call, Response<CheckPhoneResponse> response) {


                if (response.isSuccessful()) {
                    layoutprograss.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("PhoneNumber",phoneNumber.getText().toString());
                    editor.apply();

                    if (response.body().getExists()){

                        Log.d("######T######getExists", String.valueOf(response.body().getExists()));

                        if (response.body().getPassword()){
                            Log.d("######T######getExists", String.valueOf(response.body().getPassword()));
                            Intent login_intent = new Intent(MobileNumberActivity.this, LoginActivity.class);
                            startActivity(login_intent);

                        }else{
                            Log.d("#####F#######getExists", String.valueOf(response.body().getPassword()));

                            Intent login_intent = new Intent(MobileNumberActivity.this, OtpVerifyActivity.class);
                            startActivity(login_intent);

                        }

                    }else{
                        Log.d("######F######getExists", String.valueOf(response.body().getExists()));

                        Intent login_intent = new Intent(MobileNumberActivity.this, UserDetailsActivity.class);
                        startActivity(login_intent);
                    }


                } else {
                    Toast.makeText(MobileNumberActivity.this, "UserName or Password is incorrect!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CheckPhoneResponse> call, Throwable t) {
                call.cancel();

                // Toast.makeText(getContext(), "Something Went Worng", Toast.LENGTH_SHORT).show();
                Log.d("login_failedddddddddddd", "login api");
                Log.d("faileddddddddddddddd", t.getMessage());
                Log.d("faileddddddddddddddd", t.toString());
            }
        });

    }

}
