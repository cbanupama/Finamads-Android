package com.finamads.finamads.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.finamads.finamads.R;
import com.finamads.finamads.adapters.CurrentCampaginAdapter;
import com.finamads.finamads.model.GetCurrentCampaign;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentCampaginFragment extends Fragment {


    public CurrentCampaginFragment() {
        // Required empty public constructor
    }
    private SharedPreferenceUtil sharedPreferenceUtil;
    DrService apiInterface;
    CurrentCampaginAdapter currentCampaginAdapter;
    List<GetCurrentCampaign> getCurrentCampaigns = new ArrayList();
    RecyclerView current_campaign_recyclerView;
    LinearLayout linearLayout, progressLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_campagin, container, false);
        apiInterface = ApiUtils.getClient().create(DrService.class);
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        current_campaign_recyclerView = view.findViewById(R.id.current_campaign_rv);
        linearLayout = view.findViewById(R.id.current_campaign_layout);
        progressLayout = view.findViewById(R.id.no_current_campaign_layout);
        getAllJobCardInsideOfc();
        return view;
    }
 private void getAllJobCardInsideOfc() {

        Call<ArrayList<GetCurrentCampaign>> call = apiInterface.doCurrentCampaigns((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        call.enqueue(new Callback<ArrayList<GetCurrentCampaign>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCurrentCampaign>> call, Response<ArrayList<GetCurrentCampaign>> response) {

                if (response.isSuccessful()) {
                    progressLayout.setVisibility(View.GONE);

                    getCurrentCampaigns = response.body();

                    if (getCurrentCampaigns.isEmpty()){
                        progressLayout.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                   /* currentCampaginAdapter = new CurrentCampaginAdapter(getContext(), getCurrentCampaigns);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    current_campaign_recyclerView.setLayoutManager(gridLayoutManager);
                    current_campaign_recyclerView.setAdapter(currentCampaginAdapter);
                    current_campaign_recyclerView.setHasFixedSize(true);*/

                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCurrentCampaign>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });


    }
}
