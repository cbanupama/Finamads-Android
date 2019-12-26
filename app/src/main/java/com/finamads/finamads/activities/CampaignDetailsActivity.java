package com.finamads.finamads.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.finamads.finamads.R;
import com.finamads.finamads.model.GetActiveCampaigns;

public class CampaignDetailsActivity extends AppCompatActivity {

    GetActiveCampaigns getActiveCampaigns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_details);

        try {
            Intent intent = getIntent();
            getActiveCampaigns = intent.getParcelableExtra("ActiveCampaignItem");
            Log.d("#############",getActiveCampaigns.getCampaign().getAdvertiserName());
            Log.d("#############",getActiveCampaigns.getCampaign().getAdvertiserEmail());
            Log.d("#############",getActiveCampaigns.getCampaign().getAdvertiserPhone());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
