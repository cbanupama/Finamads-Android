package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("vehicle_type_id")
    @Expose
    private Integer vehicleTypeId;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("district_id")
    @Expose
    private Integer districtId;
    @SerializedName("taluk_id")
    @Expose
    private Integer talukId;
    @SerializedName("hobli_id")
    @Expose
    private Integer hobliId;
    @SerializedName("ward_id")
    @Expose
    private Integer wardId;
    @SerializedName("pin_code")
    @Expose
    private Object pinCode;
    @SerializedName("rc_front")
    @Expose
    private String rcFront;
    @SerializedName("rc_back")
    @Expose
    private String rcBack;
    @SerializedName("dl_front")
    @Expose
    private String dlFront;
    @SerializedName("dl_back")
    @Expose
    private String dlBack;
    @SerializedName("vh_front")
    @Expose
    private String vhFront;
    @SerializedName("vh_back")
    @Expose
    private String vhBack;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("number")
    @Expose
    private Object number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getTalukId() {
        return talukId;
    }

    public void setTalukId(Integer talukId) {
        this.talukId = talukId;
    }

    public Integer getHobliId() {
        return hobliId;
    }

    public void setHobliId(Integer hobliId) {
        this.hobliId = hobliId;
    }

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    public Object getPinCode() {
        return pinCode;
    }

    public void setPinCode(Object pinCode) {
        this.pinCode = pinCode;
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

    public String getDlFront() {
        return dlFront;
    }

    public void setDlFront(String dlFront) {
        this.dlFront = dlFront;
    }

    public String getDlBack() {
        return dlBack;
    }

    public void setDlBack(String dlBack) {
        this.dlBack = dlBack;
    }

    public String getVhFront() {
        return vhFront;
    }

    public void setVhFront(String vhFront) {
        this.vhFront = vhFront;
    }

    public String getVhBack() {
        return vhBack;
    }

    public void setVhBack(String vhBack) {
        this.vhBack = vhBack;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }

}
