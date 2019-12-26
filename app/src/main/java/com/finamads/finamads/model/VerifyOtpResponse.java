package com.finamads.finamads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {

    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_in")
    @Expose
    private String expiresIn;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("refresh_token")
    @Expose
    private Object refreshToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Object getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Object refreshToken) {
        this.refreshToken = refreshToken;
    }

}