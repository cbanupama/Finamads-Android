package com.finamads.finamads.fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finamads.finamads.R;
import com.finamads.finamads.adapters.SliderAdapterExample;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  {


    public HomeFragment() {
        // Required empty public constructor
    }

    CardView activeCampaigncardview,currentCampaigncardview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SliderView sliderView = view.findViewById(R.id.imageSlider);
        activeCampaigncardview = view.findViewById(R.id.cardview_activecampagin);
        currentCampaigncardview = view.findViewById(R.id.cardview_currentcampaign);


        SliderAdapterExample adapter = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        activeCampaigncardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActiveCampaignFragment jobCardsFragment = new ActiveCampaignFragment();
                FragmentTransaction(jobCardsFragment);
            }
        });

        currentCampaigncardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentCampaginFragment currentCampaginFragment = new CurrentCampaginFragment();
                FragmentTransaction(currentCampaginFragment);
            }
        });


        return view;
    }
    private void FragmentTransaction(Fragment frag) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, frag);
        fragmentTransaction.commit();

    }
}
