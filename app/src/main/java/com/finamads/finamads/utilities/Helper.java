package com.finamads.finamads.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.finamads.finamads.model.LoginResponse;
import com.finamads.finamads.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * A class that contains various functions for help in progaramming
 */
public class Helper {

 public static LoginResponse getLoggedInUserData (SharedPreferenceUtil sharedPreferenceUtil) {
        String savedUserPref = sharedPreferenceUtil.getStringPreferenceData(Constants.USER_DATA, null);
        if (savedUserPref != null)
            return new Gson().fromJson(savedUserPref, new TypeToken<LoginResponse>() {
            }.getType());
        else return null;
    }

 public static void setLoggedInUserData(SharedPreferenceUtil sharedPreferenceUtil, LoginResponse user) {
        sharedPreferenceUtil.setStringPreferenceData(Constants.USER_DATA, new Gson().toJson(user, new TypeToken<LoginResponse>() {
        }.getType()));
    }

    public static void closeKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void setLoggedInUserr(SharedPreferenceUtil sharedPreferenceUtil, LoginResponse user) {
        sharedPreferenceUtil.setStringPreference(Constants.USER, new Gson().toJson(user, new TypeToken<LoginResponse>() {
        }.getType()));
    }

    public static LoginResponse getLoggedInUserr(SharedPreferenceUtil sharedPreferenceUtil) {
        String savedUserPref = sharedPreferenceUtil.getStringPreference(Constants.USER, null);
        if (savedUserPref != null)
            return new Gson().fromJson(savedUserPref, new TypeToken<LoginResponse>() {
            }.getType());
        else return null;
    }
}
