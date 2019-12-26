package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVehicleInput {

    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("taluk_id")
    @Expose
    private String talukId;
    @SerializedName("ward_id")
    @Expose
    private String wardId;
    @SerializedName("hobli_id")
    @Expose
    private String hobliId;
    @SerializedName("vehicle_type_id")
    @Expose
    private String vehicleTypeId;
    @SerializedName("dl_back")
    @Expose
    private String dlBack;
    @SerializedName("dl_front")
    @Expose
    private String dlFront;
    @SerializedName("vh_back")
    @Expose
    private String vhBack;
    @SerializedName("vh_front")
    @Expose
    private String vhFront;
    @SerializedName("rc_front")
    @Expose
    private String rcFront;
    @SerializedName("rc_back")
    @Expose
    private String rcBack;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;


    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getTalukId() {
        return talukId;
    }

    public void setTalukId(String talukId) {
        this.talukId = talukId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getHobliId() {
        return hobliId;
    }

    public void setHobliId(String hobliId) {
        this.hobliId = hobliId;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getDlBack() {
        return dlBack;
    }

    public void setDlBack(String dlBack) {
        this.dlBack = dlBack;
    }

    public String getDlFront() {
        return dlFront;
    }

    public void setDlFront(String dlFront) {
        this.dlFront = dlFront;
    }

    public String getVhBack() {
        return vhBack;
    }

    public void setVhBack(String vhBack) {
        this.vhBack = vhBack;
    }

    public String getVhFront() {
        return vhFront;
    }

    public void setVhFront(String vhFront) {
        this.vhFront = vhFront;
    }

    public String getRcFront() {
        return rcFront;
    }

    public void setRcFront(String rcFront) {
        this.rcFront = rcFront;
    }

    public String getRcBack() {
        return rcBack;
    }

    public void setRcBack(String rcBack) {
        this.rcBack = rcBack;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
