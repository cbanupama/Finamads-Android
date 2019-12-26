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
import com.finamads.finamads.model.GetDistricts;
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
import static com.finamads.finamads.fragments.StateFragment.MY_PREFS_STATE;

public class DistructFragment extends DialogFragment {

    DrService apiInterface;
    private SharedPreferenceUtil sharedPreferenceUtil;
    ArrayList<GetDistricts> getUsers = new ArrayList<GetDistricts>();
    List<String> distlistname = new ArrayList<String>();
    List<Integer> distlistid = new ArrayList<Integer>();
    MultiLineRadioGroup mDepartmentRadioGroup;
    public static String personname = "Select the contact person name";
    public static int distid = 0;

    public static final String MY_PREFS_TALUK = "MyPrefsTaluk";

    public String distURL;

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
        View view = inflater.inflate(R.layout.fragment_districut_dialog, container, false);

        apiInterface = ApiUtils.getClient().create(DrService.class);
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        mDepartmentRadioGroup = view.findViewById(R.id.pmulti_line_radio_group);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_STATE, MODE_PRIVATE);
        String statename = prefs.getString("stateName", "No name defined");//"No name defined" is the default value.
        int stateid = prefs.getInt("stateID", 0); //0 is the default value.
        Log.d("###########sateid", String.valueOf(stateid));
        Log.d("###########stateName", statename);
        if (stateid != 0) {

            distURL = "http://api.finamads.com/api/places/districts?filter[state_id]=" + stateid;

        } else {

            //Toast.makeText(getContext(), "Please Select the Department!!", Toast.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(getContext(), "Please Select the State!!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

        getDistruct();

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

    private void getDistruct() {

        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<ArrayList<GetDistricts>> call = apiInterface.getDistricts(distURL);
        call.enqueue(new Callback<ArrayList<GetDistricts>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistricts>> call, final Response<ArrayList<GetDistricts>> response) {

                if (response.isSuccessful()) {

                    if (response.body().isEmpty()){

                        Toast.makeText(getContext(), "There are No Districts Under this State!!", Toast.LENGTH_LONG).show();

                    }else{

                        Log.d("#######Sucess########", "");
                        for (int i = 0; i < response.body().size(); i++) {

                            mDepartmentRadioGroup.addButtons(response.body().get(i).getName());
                            distlistname.add(response.body().get(i).getName());
                            distlistid.add(response.body().get(i).getId());

                        }

                        mDepartmentRadioGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(ViewGroup group, RadioButton button) {


                                UserLocationActivity.distSpinner.setText(button.getText().toString());
                                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_TALUK, MODE_PRIVATE).edit();
                                distid = distlistid.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex());
                                editor.putInt("distID", distid);
                                editor.putString("distName", button.getText().toString());
                                if (response.body().get(mDepartmentRadioGroup.getCheckedRadioButtonIndex()).getHasWards()){

                                    editor.putString("hasWards", "hasWards");
                                }

                                editor.apply();
                                Log.d("#######distID########", distlistname.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex()));
                                Log.d("#####distName#####", String.valueOf(distlistid.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex())));

                                dismiss();

                            }
                        });

                    }

                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistricts>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });

    }

}
