package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccpectCampaignInput {

    @SerializedName("ad_offer_id")
    @Expose
    private Integer adOfferId;

    public Integer getAdOfferId() {
        return adOfferId;
    }

    public void setAdOfferId(Integer adOfferId) {
        this.adOfferId = adOfferId;
    }

}
