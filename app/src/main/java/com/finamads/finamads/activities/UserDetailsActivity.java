package com.finamads.finamads.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finamads.finamads.R;
import com.finamads.finamads.model.RegisterInput;
import com.finamads.finamads.model.RegisterResponse;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.DrService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finamads.finamads.activities.MobileNumberActivity.MY_PREFS_NAME;

public class UserDetailsActivity extends AppCompatActivity {

    Button submitDetails;
    DrService apiInterface;
    RegisterInput registerInput;

    EditText regName, regEmail, password, repassword,regPhone;
    String phoneNumber;
    public static final String MY_PREFS_USER = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        submitDetails = findViewById(R.id.submit_details);

        regName = findViewById(R.id.reg_name);
        regPhone = findViewById(R.id.reg_mobile);
        password = findViewById(R.id.reg_pwd);
        repassword = findViewById(R.id.re_password);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("PhoneNumber", "No name defined");
        if (!phoneNumber.isEmpty()) {

         regPhone.setText(phoneNumber);
        }


        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerInput = new RegisterInput();
                registerInput.setName(regName.getText().toString());

                registerInput.setPassword(password.getText().toString());
                registerInput.setPasswordConfirmation(repassword.getText().toString());
                registerInput.setPhone(regPhone.getText().toString());
                submitRegistrationForm();
            }
        });

    }

    private void submitRegistrationForm() {


        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<RegisterResponse> call = apiInterface.submitUser(registerInput);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {


                    Intent i = new Intent(UserDetailsActivity.this, OtpVerifyActivity.class);
                    startActivity(i);
                } else {

                    Toast.makeText(UserDetailsActivity.this, "Please give 8 digit Password and valid Email ID!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                call.cancel();

                Toast.makeText(UserDetailsActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "add family api");
            }
        });

    }

}
