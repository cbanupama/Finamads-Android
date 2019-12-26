package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccpectCampaginResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("printer")
    @Expose
    private Printer printer;
    @SerializedName("campaign")
    @Expose
    private Campaign campaign;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }


}
