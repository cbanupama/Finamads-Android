package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCurrentCampaign {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("advertisement_id")
    @Expose
    private Integer advertisementId;
    @SerializedName("vehicle_id")
    @Expose
    private Integer vehicleId;
    @SerializedName("printer_id")
    @Expose
    private Integer printerId;
    @SerializedName("start_date")
    @Expose
    private Object startDate;
    @SerializedName("end_date")
    @Expose
    private Object endDate;
    @SerializedName("start_image_by_vehicle")
    @Expose
    private Object startImageByVehicle;
    @SerializedName("start_image_by_printer")
    @Expose
    private Object startImageByPrinter;
    @SerializedName("end_image_by_vehicle")
    @Expose
    private Object endImageByVehicle;
    @SerializedName("end_image_by_printer")
    @Expose
    private Object endImageByPrinter;
    @SerializedName("vehicle_code")
    @Expose
    private Object vehicleCode;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("can_be_paid")
    @Expose
    private Object canBePaid;
    @SerializedName("ended")
    @Expose
    private Integer ended;
    @SerializedName("is_running")
    @Expose
    private Integer isRunning;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("printer")
    @Expose
    private Printer printer;
    @SerializedName("campaign")
    @Expose
    private Campaign campaign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Integer advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Object getStartImageByVehicle() {
        return startImageByVehicle;
    }

    public void setStartImageByVehicle(Object startImageByVehicle) {
        this.startImageByVehicle = startImageByVehicle;
    }

    public Object getStartImageByPrinter() {
        return startImageByPrinter;
    }

    public void setStartImageByPrinter(Object startImageByPrinter) {
        this.startImageByPrinter = startImageByPrinter;
    }

    public Object getEndImageByVehicle() {
        return endImageByVehicle;
    }

    public void setEndImageByVehicle(Object endImageByVehicle) {
        this.endImageByVehicle = endImageByVehicle;
    }

    public Object getEndImageByPrinter() {
        return endImageByPrinter;
    }

    public void setEndImageByPrinter(Object endImageByPrinter) {
        this.endImageByPrinter = endImageByPrinter;
    }

    public Object getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(Object vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getCanBePaid() {
        return canBePaid;
    }

    public void setCanBePaid(Object canBePaid) {
        this.canBePaid = canBePaid;
    }

    public Integer getEnded() {
        return ended;
    }

    public void setEnded(Integer ended) {
        this.ended = ended;
    }

    public Integer getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(Integer isRunning) {
        this.isRunning = isRunning;
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
