package com.finamads.finamads.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.finamads.finamads.R;
import com.finamads.finamads.model.LoginResponse;
import com.finamads.finamads.model.OtpInput;
import com.finamads.finamads.model.OtpResponse;
import com.finamads.finamads.model.VerifyOtpInput;
import com.finamads.finamads.model.VerifyOtpResponse;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.Helper;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finamads.finamads.activities.MobileNumberActivity.MY_PREFS_NAME;
import static com.finamads.finamads.activities.UserDetailsActivity.MY_PREFS_USER;

public class OtpVerifyActivity extends AppCompatActivity {
    Button submitotp;
    private OtpView otpView;
    DrService apiInterface;
    String phoneNumber;
    OtpInput otpInput;
    VerifyOtpInput verifyOtpInput;
    LinearLayout layoutprograss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        submitotp = findViewById(R.id.submit_otp);
        otpView = findViewById(R.id.otp_view);
        layoutprograss = findViewById(R.id.otp_progress_layout);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("PhoneNumber", "No name defined");
        if (!phoneNumber.isEmpty()) {
            otpInput = new OtpInput();
            otpInput.setPhone(phoneNumber);
            getOtp();
        }

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                // do Stuff
                Log.d("onOtpCompleted=>", otp);
                verifyOtpInput = new VerifyOtpInput();
                verifyOtpInput.setOtp(otp);
                verifyOtpInput.setPhone(phoneNumber);
                layoutprograss.setVisibility(View.VISIBLE);
                SubmitOtp();
            }
        });


    }

    private void getOtp() {


        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<OtpResponse> call = apiInterface.getOtp(otpInput);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {

                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    Toast.makeText(OtpVerifyActivity.this, "Please enter the OTP!!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(OtpVerifyActivity.this, "Pleaseeeeeeeeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                call.cancel();

                Toast.makeText(OtpVerifyActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "OtpVerify activity api");
            }
        });

    }

    private void SubmitOtp() {


        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<LoginResponse> call = apiInterface.submitOtp(verifyOtpInput);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    if (response.body().getVehicle() != null) {
                        SharedPreferences.Editor editorr = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editorr.putString("vehicle_id", String.valueOf(response.body().getVehicle().getVehicleTypeId()));
                        editorr.apply();
                    }

                    layoutprograss.setVisibility(View.GONE);
                    Toast.makeText(OtpVerifyActivity.this, "OTP verified Succesfully!!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_USER, MODE_PRIVATE).edit();
                    editor.putString("UserDetailsActivity", "done");
                    editor.apply();

                    Intent intent = new Intent(OtpVerifyActivity.this, SelectVehicleActivity.class);
                    startActivity(intent);
                } else {
                    layoutprograss.setVisibility(View.GONE);
                    Toast.makeText(OtpVerifyActivity.this, "Incorrect OTP!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                layoutprograss.setVisibility(View.GONE);
                Toast.makeText(OtpVerifyActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "OtpVerify activity api");
            }
        });

    }

}
