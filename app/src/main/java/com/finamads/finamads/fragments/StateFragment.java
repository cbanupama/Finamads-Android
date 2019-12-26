package com.finamads.finamads.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.finamads.finamads.R;
import com.finamads.finamads.activities.UserLocationActivity;
import com.finamads.finamads.model.GetStates;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.SharedPreferenceUtil;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class StateFragment extends DialogFragment {

    DrService apiInterface;
    private SharedPreferenceUtil sharedPreferenceUtil;
    List<String> statelistname = new ArrayList<String>();
    List<Integer> statelistid = new ArrayList<Integer>();
    MultiLineRadioGroup mDepartmentRadioGroup;
    public static String personname = "Select the contact person name";
    public static int stateid = 0;
    public String URL;
    public static final String MY_PREFS_STATE = "MyPrefsState";



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert Dialog");
        builder.setMessage("Alert Dialog inside DialogFragment");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_dialog, container, false);

        apiInterface = ApiUtils.getClient().create(DrService.class);
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        mDepartmentRadioGroup = view.findViewById(R.id.pmulti_line_radio_group);

        statelistname.removeAll(statelistname);
        statelistid.removeAll(statelistid);


        getStates();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("API123", "onCreate");

        boolean setFullScreen = false;
        if (getArguments() != null) {
            setFullScreen = getArguments().getBoolean("fullScreen");
        }

        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }

    private void getStates() {
        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<ArrayList<GetStates>> call = apiInterface.getStates();
        call.enqueue(new Callback<ArrayList<GetStates>>() {
            @Override
            public void onResponse(Call<ArrayList<GetStates>> call, Response<ArrayList<GetStates>> response) {

                if (response.isSuccessful()) {

                    Log.d("#######Sucess########", "");
                    for (int i = 0; i < response.body().size(); i++) {

                        mDepartmentRadioGroup.addButtons(response.body().get(i).getName());
                        statelistname.add(response.body().get(i).getName());
                        statelistid.add(response.body().get(i).getId());

                    }

                    mDepartmentRadioGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(ViewGroup group, RadioButton button) {




                            UserLocationActivity.stateSpinner.setText(button.getText().toString());

                            SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_STATE, MODE_PRIVATE).edit();
                            stateid = statelistid.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex());
                            editor.putInt("stateID", stateid);
                            editor.putString("stateName", button.getText().toString());
                            editor.apply();
                            Log.d("#######statename######",statelistname.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex()));
                            Log.d("#####stateID#####", String.valueOf(stateid));


                            dismiss();

                        }
                    });
                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetStates>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });

    }

}
