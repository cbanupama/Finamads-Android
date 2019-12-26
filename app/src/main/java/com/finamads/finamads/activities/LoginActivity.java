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
import android.widget.TextView;
import android.widget.Toast;

import com.finamads.finamads.R;
import com.finamads.finamads.model.LoginInput;
import com.finamads.finamads.model.LoginResponse;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.Helper;
import com.finamads.finamads.utilities.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finamads.finamads.activities.MobileNumberActivity.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {

    TextView loginWidOtp;
    Button submit;
    DrService apiInterface;
    LoginInput loginInput;
    EditText loginName, loginPassword;
    private SharedPreferenceUtil sharedPreferenceUtil;
    boolean isLoggedIn;
    String phoneNumber;
    LinearLayout layoutprograss;
    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginWidOtp = findViewById(R.id.login_otp);
        submit = findViewById(R.id.btn_signin);
        loginName = findViewById(R.id.login_name);
        loginPassword = findViewById(R.id.login_password);
        layoutprograss = findViewById(R.id.login_progress_layout);
        sharedPreferenceUtil = new SharedPreferenceUtil(this);
        isLoggedIn = Helper.getLoggedInUserr(sharedPreferenceUtil) != null;
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("PhoneNumber", "No name defined");
        if (!phoneNumber.isEmpty()) {
            loginName.setText(phoneNumber);
            loginPassword.requestFocus();
        }

        loginWidOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, OtpVerifyActivity.class);
                startActivity(i);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutprograss.setVisibility(View.VISIBLE);
                loginInput = new LoginInput();
                loginInput.setUsername(loginName.getText().toString());
                loginInput.setPassword(loginPassword.getText().toString());
                submitLoginForm();

            }
        });

    }

    private void submitLoginForm() {


        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<LoginResponse> call = apiInterface.submitLogin(loginInput);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {

                    sharedPreferenceUtil.setStringPreference(Constants.KEY_API_KEY, "Bearer " + response.body().getAccessToken());
                    Helper.setLoggedInUserr(sharedPreferenceUtil, response.body());
                    loginResponse = response.body();
                    Helper.setLoggedInUserData(sharedPreferenceUtil, loginResponse);

                    if (response.body().getVehicle() != null) {
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("vehicle_id", String.valueOf(response.body().getVehicle().getVehicleTypeId()));
                        editor.apply();
                    }

                    Log.d("####getUserToken####", String.valueOf(response.body().getAccessToken()));
                    layoutprograss.setVisibility(View.GONE);
                    if (response.body().getUser().getOtpVerified()) {
                        if (response.body().getVehicle() == null) {
                            Intent i = new Intent(LoginActivity.this, SelectVehicleActivity.class);
                            startActivity(i);
                        } else if ((response.body().getVehicle().getRcBack() == null ||
                                response.body().getVehicle().getRcFront() == null ||
                                response.body().getVehicle().getDlBack() == null ||
                                response.body().getVehicle().getDlFront() == null ||
                                response.body().getVehicle().getVhBack() == null ||
                                response.body().getVehicle().getVhFront() == null)) {

                            Intent i = new Intent(LoginActivity.this, KycActivity.class);
                            startActivity(i);

                        } else {

                            Toast.makeText(LoginActivity.this, "Locationnnnnnnnnnn!!!!!!!!!", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Intent i = new Intent(LoginActivity.this, OtpVerifyActivity.class);
                        startActivity(i);
                    }

                } else {
                    layoutprograss.setVisibility(View.GONE);
                    loginPassword.getText().clear();
                    loginPassword.requestFocus();
                    Toast.makeText(LoginActivity.this, "Incorrect Password!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();

                Toast.makeText(LoginActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "login activity api");
                layoutprograss.setVisibility(View.GONE);
            }
        });

    }
}
