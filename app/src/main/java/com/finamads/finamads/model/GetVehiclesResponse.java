package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVehiclesResponse {

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
    private Object stateId;
    @SerializedName("district_id")
    @Expose
    private Object districtId;
    @SerializedName("taluk_id")
    @Expose
    private Object talukId;
    @SerializedName("hobli_id")
    @Expose
    private Object hobliId;
    @SerializedName("ward_id")
    @Expose
    private Object wardId;
    @SerializedName("pin_code")
    @Expose
    private Object pinCode;
    @SerializedName("rc_front")
    @Expose
    private Object rcFront;
    @SerializedName("rc_back")
    @Expose
    private Object rcBack;
    @SerializedName("dl_front")
    @Expose
    private String dlFront;
    @SerializedName("dl_back")
    @Expose
    private String dlBack;
    @SerializedName("vh_front")
    @Expose
    private Object vhFront;
    @SerializedName("vh_back")
    @Expose
    private Object vhBack;
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

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public Object getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Object districtId) {
        this.districtId = districtId;
    }

    public Object getTalukId() {
        return talukId;
    }

    public void setTalukId(Object talukId) {
        this.talukId = talukId;
    }

    public Object getHobliId() {
        return hobliId;
    }

    public void setHobliId(Object hobliId) {
        this.hobliId = hobliId;
    }

    public Object getWardId() {
        return wardId;
    }

    public void setWardId(Object wardId) {
        this.wardId = wardId;
    }

    public Object getPinCode() {
        return pinCode;
    }

    public void setPinCode(Object pinCode) {
        this.pinCode = pinCode;
    }

    public Object getRcFront() {
        return rcFront;
    }

    public void setRcFront(Object rcFront) {
        this.rcFront = rcFront;
    }

    public Object getRcBack() {
        return rcBack;
    }

    public void setRcBack(Object rcBack) {
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

    public Object getVhFront() {
        return vhFront;
    }

    public void setVhFront(Object vhFront) {
        this.vhFront = vhFront;
    }

    public Object getVhBack() {
        return vhBack;
    }

    public void setVhBack(Object vhBack) {
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
