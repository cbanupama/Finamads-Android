package com.finamads.finamads.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finamads.finamads.R;
import com.finamads.finamads.adapters.VehicleTypeAdapter;
import com.finamads.finamads.model.GetVehicleType;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.SharedPreferenceUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectVehicleActivity extends AppCompatActivity {

    private static final String TAG = "SelectVehicleActivity";

    //vars
  /*  private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();*/
    DrService apiInterface;
    private SharedPreferenceUtil sharedPreferenceUtil;
    String URL;
    LinearLayout progressLayout;
    RecyclerView recyclerView;
    VehicleTypeAdapter adapter;
    ArrayList<GetVehicleType> getVehicleTypesList = new ArrayList<GetVehicleType>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        Log.d(TAG, "onCreate: started.");
        recyclerView = findViewById(R.id.recyclerv_view);
        apiInterface = ApiUtils.getClient().create(DrService.class);
        progressLayout = findViewById(R.id.vehicle_layout);
        sharedPreferenceUtil = new SharedPreferenceUtil(SelectVehicleActivity.this);
        //initImageBitmaps();
        getVehicleTypes();
    }


    private void getVehicleTypes() {

        Call<ArrayList<GetVehicleType>> call = apiInterface.getVehicleType((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        call.enqueue(new Callback<ArrayList<GetVehicleType>>() {
            @Override
            public void onResponse(Call<ArrayList<GetVehicleType>> call, Response<ArrayList<GetVehicleType>> response) {

                if (response.isSuccessful()) {

                    progressLayout.setVisibility(View.GONE);
                    getVehicleTypesList.addAll(response.body());
                    Log.d("#######getName########", response.body().get(0).getName());

                    adapter = new VehicleTypeAdapter(SelectVehicleActivity.this,getVehicleTypesList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SelectVehicleActivity.this));

                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetVehicleType>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });

    }

}






















