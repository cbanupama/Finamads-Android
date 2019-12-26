package com.finamads.finamads.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.finamads.finamads.R;
import com.finamads.finamads.adapters.ActiveCampaginAdapter;
import com.finamads.finamads.model.GetActiveCampaigns;
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
public class ActiveCampaignFragment extends Fragment {

    RecyclerView inside_jobcards_recyclerView;
    List<GetActiveCampaigns> getCampaigns = new ArrayList();
    DrService apiInterface;
    public ActiveCampaginAdapter adapterInside;
    private SharedPreferenceUtil sharedPreferenceUtil;
    LinearLayout linearLayout, progressLayout;

    public ActiveCampaignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_campaign, container, false);

        apiInterface = ApiUtils.getClient().create(DrService.class);
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        inside_jobcards_recyclerView = view.findViewById(R.id.inside_jobcards_rv);
        linearLayout = view.findViewById(R.id.no_campaign_layout);
        progressLayout = view.findViewById(R.id.job_progress_layout);
        getAllJobCardInsideOfc();
        return view;
    }

    private void getAllJobCardInsideOfc() {

        Call<ArrayList<GetActiveCampaigns>> call = apiInterface.doActiveCampaigns((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        call.enqueue(new Callback<ArrayList<GetActiveCampaigns>>() {
            @Override
            public void onResponse(Call<ArrayList<GetActiveCampaigns>> call, Response<ArrayList<GetActiveCampaigns>> response) {

                if (response.isSuccessful()) {
                    progressLayout.setVisibility(View.GONE);


                    getCampaigns = response.body();

                    if (getCampaigns.isEmpty()){

                        linearLayout.setVisibility(View.VISIBLE);
                    }

                    adapterInside = new ActiveCampaginAdapter(getContext(), getCampaigns);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    inside_jobcards_recyclerView.setLayoutManager(gridLayoutManager);
                    inside_jobcards_recyclerView.setAdapter(adapterInside);
                    inside_jobcards_recyclerView.setHasFixedSize(true);

                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetActiveCampaigns>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });


    }

}
