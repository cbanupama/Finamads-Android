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
import com.finamads.finamads.model.GetHobli;
import com.finamads.finamads.model.GetTaluks;
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
import static com.finamads.finamads.fragments.DistructFragment.MY_PREFS_TALUK;
import static com.finamads.finamads.fragments.TalukFragment.MY_PREFS_HOBLI;

public class HobliFragment extends DialogFragment {

    DrService apiInterface;
    private SharedPreferenceUtil sharedPreferenceUtil;
    ArrayList<GetHobli> getUsers = new ArrayList<GetHobli>();
    MultiLineRadioGroup mDepartmentRadioGroup;
    public static String personname = "Select the contact person name";
    public static int hobliid = 0;
    public String URL;
    public static final String MY_PREFS = "MyPrefs";

    public String hobliURL;
    List<String> hoblilistname = new ArrayList<String>();
    List<Integer> hoblilistid = new ArrayList<Integer>();

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
        View view = inflater.inflate(R.layout.fragment_hobli_dialog, container, false);

        apiInterface = ApiUtils.getClient().create(DrService.class);
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        mDepartmentRadioGroup = view.findViewById(R.id.pmulti_line_radio_group);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_HOBLI, MODE_PRIVATE);
        String talukname = prefs.getString("talukName", "No name defined");//"No name defined" is the default value.
        int talukid = prefs.getInt("talukID", 0); //0 is the default value.
        Log.d("###########distid", String.valueOf(talukid));
        Log.d("###########distname", talukname);

        if (talukid != 0) {

            hobliURL = "http://api.finamads.com/api/places/hoblis?filter[taluk_id]=" + talukid;
        } else {

            //Toast.makeText(getContext(), "Please Select the Department!!", Toast.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(getContext(), "Please Select the Department!!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

        getHobli();

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

    private void getHobli() {

        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<ArrayList<GetHobli>> call = apiInterface.getHobli(hobliURL);
        call.enqueue(new Callback<ArrayList<GetHobli>>() {
            @Override
            public void onResponse(Call<ArrayList<GetHobli>> call, Response<ArrayList<GetHobli>> response) {

                if (response.isSuccessful()) {

                    if (response.body().isEmpty()){

                        Toast.makeText(getContext(), "There are No Hobli Under this State!!", Toast.LENGTH_LONG).show();

                    }else{
                        Log.d("#######Sucess########", "");
                        for (int i = 0; i < response.body().size(); i++) {

                            mDepartmentRadioGroup.addButtons(response.body().get(i).getName());
                            hoblilistname.add(response.body().get(i).getName());
                            hoblilistid.add(response.body().get(i).getId());

                        }

                        mDepartmentRadioGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(ViewGroup group, RadioButton button) {


                                UserLocationActivity.hobliSpinner.setText(button.getText().toString());
                                SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
                                hobliid = hoblilistid.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex());
                                editor.putInt("hobliID", hobliid);
                                editor.putString("hobliName", button.getText().toString());
                                editor.apply();
                                Log.d("#######distID########",hoblilistname.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex()));
                                Log.d("#####distName#####", String.valueOf(hoblilistid.get(mDepartmentRadioGroup.getCheckedRadioButtonIndex())));

                                dismiss();

                            }
                        });

                    }


                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetHobli>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });

    }

}
