package com.finamads.finamads.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetActiveCampaigns implements Parcelable {

    @SerializedName("campaign")
    @Expose
    private Campaign campaign;
    @SerializedName("printer")
    @Expose
    private Printer printer;

    protected GetActiveCampaigns(Parcel in) {
    }

    public static final Creator<GetActiveCampaigns> CREATOR = new Creator<GetActiveCampaigns>() {
        @Override
        public GetActiveCampaigns createFromParcel(Parcel in) {
            return new GetActiveCampaigns(in);
        }

        @Override
        public GetActiveCampaigns[] newArray(int size) {
            return new GetActiveCampaigns[size];
        }
    };

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
