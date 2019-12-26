package com.finamads.finamads.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finamads.finamads.R;
import com.finamads.finamads.fragments.DistructFragment;
import com.finamads.finamads.fragments.HobliFragment;
import com.finamads.finamads.fragments.TalukFragment;
import com.finamads.finamads.fragments.StateFragment;
import com.finamads.finamads.model.AddVehicleInput;
import com.finamads.finamads.model.GetDistricts;
import com.finamads.finamads.model.GetHobli;
import com.finamads.finamads.model.GetStates;
import com.finamads.finamads.model.GetTaluks;
import com.finamads.finamads.model.LoginResponse;
import com.finamads.finamads.utilities.Api;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Client;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.Helper;
import com.finamads.finamads.utilities.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finamads.finamads.adapters.VehicleTypeAdapter.MY_PREFS_NAME;
import static com.finamads.finamads.fragments.DistructFragment.MY_PREFS_TALUK;
import static com.finamads.finamads.fragments.HobliFragment.MY_PREFS;
import static com.finamads.finamads.fragments.StateFragment.MY_PREFS_STATE;
import static com.finamads.finamads.fragments.TalukFragment.MY_PREFS_HOBLI;

public class UserLocationActivity extends AppCompatActivity implements View.OnClickListener {

    public static TextView stateSpinner, distSpinner, talukSpinner, hobliSpinner;
    TextView confirmLocation, loc;
    LoginResponse loginResponse;
    Call<AddVehicleInput> call;
    String venName;
    LinearLayout layoutprograss;
    public String userloc = "", stateloc, distloc, talukloc, hobilloc;


    Button submitKyc;
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);


        sharedPreferenceUtil = new SharedPreferenceUtil(UserLocationActivity.this);
        loginResponse = Helper.getLoggedInUserData(sharedPreferenceUtil);
        stateSpinner = findViewById(R.id.state_spinner);
        distSpinner = findViewById(R.id.district_spinner);
        talukSpinner = findViewById(R.id.taluk_wards_spinner);
        hobliSpinner = findViewById(R.id.hobli_spinner);
        confirmLocation = findViewById(R.id.confirm_location);
        loc = findViewById(R.id.location);
        submitKyc = findViewById(R.id.submit_kyc_location);
        layoutprograss = findViewById(R.id.UserLocationActivity_progress_layout);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        venName = prefs.getString("vehicle_id", "No name defined");//"No name defined" is the default value.


        Log.d("##############", venName);

        confirmLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userloc = stateSpinner.getText().toString() + " , " + distSpinner.getText().toString() + " , " + talukSpinner.getText().toString() + " , " + hobliSpinner.getText().toString();

                loc.setText(userloc);
            }
        });

        submitKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences stateprefs = getSharedPreferences(MY_PREFS_STATE, MODE_PRIVATE);
                String statename = stateprefs.getString("stateName", "No name defined");//"No name defined" is the default value.
                int stateid = stateprefs.getInt("stateID", 0); //0 is the default value.
                Log.d("###########sateid", String.valueOf(stateid));
                Log.d("###########stateName", statename);

                SharedPreferences distprefs = getSharedPreferences(MY_PREFS_TALUK, MODE_PRIVATE);
                String distname = distprefs.getString("distName", "No name defined");//"No name defined" is the default value.
                int distid = distprefs.getInt("distID", 0); //0 is the default value.
                String haswards = distprefs.getString("hasWards","No woards");
                Log.d("###########distid", String.valueOf(distid));
                Log.d("###########distname", distname);

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_HOBLI, MODE_PRIVATE);
                String talukname = prefs.getString("talukName", "No name defined");//"No name defined" is the default value.
                int talukid = prefs.getInt("talukID", 0); //0 is the default value.
                Log.d("###########talukid", String.valueOf(talukid));
                Log.d("###########talukname", talukname);

                SharedPreferences hobliprefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
                String hobliname = hobliprefs.getString("hobliName", "No name defined");//"No name defined" is the default value.
                int hobliid = hobliprefs.getInt("hobliID", 0); //0 is the default value.
                Log.d("###########hobliid", String.valueOf(hobliid));
                Log.d("###########hobliname", hobliname);

                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                builder.addFormDataPart("vehicle_type_id", venName);
                if (stateid != 0) {
                    builder.addFormDataPart("state_id", String.valueOf(stateid));
                }

                if (distid != 0) {
                    builder.addFormDataPart("district_id", String.valueOf(distid));
                }

                if (haswards.matches("hasWards")){
                    if (talukid != 0) {
                        builder.addFormDataPart("ward_id", String.valueOf(talukid));
                    }

                }else {

                    if (talukid != 0) {
                        builder.addFormDataPart("taluk_id", String.valueOf(talukid));
                    }
                }

                if (hobliid != 0) {
                    builder.addFormDataPart("hobli_id", String.valueOf(hobliid));
                }


                MultipartBody requestBody = builder.build();

                submitUserLocation(requestBody);
            }
        });

        //submitKyc.setOnClickListener(this);
        stateSpinner.setOnClickListener(this);
        distSpinner.setOnClickListener(this);
        talukSpinner.setOnClickListener(this);
        hobliSpinner.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.state_spinner:
                StateFragment stateFragment = new StateFragment();

                Bundle statebundle = new Bundle();
                statebundle.putBoolean("notAlertDialog", true);

                stateFragment.setArguments(statebundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                stateFragment.show(ft, "dialog");

                break;
            case R.id.district_spinner: {
                DistructFragment distructFragment = new DistructFragment();

                Bundle districtbundle = new Bundle();
                districtbundle.putBoolean("notAlertDialog", true);

                distructFragment.setArguments(districtbundle);

                FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
                Fragment prevv = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prevv != null) {
                    ftt.remove(prevv);
                }
                ftt.addToBackStack(null);


                distructFragment.show(ftt, "dialog");
                break;
            }

            case R.id.submit_kyc: {
                Intent intent = new Intent(UserLocationActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.taluk_wards_spinner:

                TalukFragment dialogFragment = new TalukFragment();

                Bundle bundle = new Bundle();
                bundle.putBoolean("notAlertDialog", true);

                dialogFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialog");
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.addToBackStack(null);


                dialogFragment.show(fragmentTransaction, "dialog");
                break;


            case R.id.hobli_spinner:

                HobliFragment hobliFragment = new HobliFragment();

                Bundle b = new Bundle();
                b.putBoolean("notAlertDialog", true);

                hobliFragment.setArguments(b);

                FragmentTransaction fttt = getSupportFragmentManager().beginTransaction();
                Fragment frag = getSupportFragmentManager().findFragmentByTag("dialog");
                if (frag != null) {
                    fttt.remove(frag);
                }
                fttt.addToBackStack(null);


                hobliFragment.show(fttt, "dialog");

                break;


        }
    }


    private void submitUserLocation(MultipartBody requestBody) {


        Api api = Client.api();
        // prepare headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", (sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        headers.put("Accept", "application/json");

        if (loginResponse.getVehicle() == null) {
            call = api.uploadPostFiles(headers, requestBody);
        } else {
            call = api.uploadPutFiles(headers, requestBody,loginResponse.getVehicle().getId());
        }
        call.enqueue(new Callback<AddVehicleInput>() {
            @Override
            public void onResponse(Call<AddVehicleInput> call, Response<AddVehicleInput> response) {
                System.out.println("response received");
                System.out.println(response.body());
                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {

                    layoutprograss.setVisibility(View.GONE);
                    Toast.makeText(UserLocationActivity.this, "Images Uploaded Successfully!!", Toast.LENGTH_LONG).show();

                    SharedPreferences stateprefs = getSharedPreferences(MY_PREFS_STATE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = stateprefs.edit();
                    editor.clear();
                    editor.apply();


                    SharedPreferences distprefs = getSharedPreferences(MY_PREFS_TALUK, MODE_PRIVATE);
                    SharedPreferences.Editor deditor = distprefs.edit();
                    deditor.clear();
                    deditor.apply();

                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_HOBLI, MODE_PRIVATE);
                    SharedPreferences.Editor peditor = prefs.edit();
                    peditor.clear();
                    peditor.apply();

                    SharedPreferences hobliprefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor heditor = hobliprefs.edit();
                    heditor.clear();
                    heditor.apply();


                    Intent intent = new Intent(UserLocationActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    layoutprograss.setVisibility(View.GONE);
                    Toast.makeText(UserLocationActivity.this, "Please Check internet connection and try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddVehicleInput> call, Throwable t) {
                System.out.println("error received");
                System.out.println(t.getMessage());
                System.out.println(t.toString());
                call.cancel();
                //layoutprograss.setVisibility(View.GONE);
                Toast.makeText(UserLocationActivity.this, "Something Went Worng", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "KycActivity");
            }
        });
    }

}
