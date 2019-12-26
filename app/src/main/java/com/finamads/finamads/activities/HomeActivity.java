package com.finamads.finamads.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.finamads.finamads.R;
import com.finamads.finamads.fragments.HomeFragment;
import com.finamads.finamads.model.FcmToken;
import com.finamads.finamads.model.FcmTokenResponse;
import com.finamads.finamads.model.GetUser;
import com.finamads.finamads.model.User;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.Helper;
import com.finamads.finamads.utilities.SharedPreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int fragname = 0;
    private static String CONFIRM_TAG = "confirmtag";
    public static String profil_name = "Fidelitus";
    DrService apiInterface;
    //User user;
    FcmToken tokenInput;
    String token;
    private SharedPreferenceUtil sharedPreferenceUtil;
    Call<FcmTokenResponse> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isConnected();
        sharedPreferenceUtil = new SharedPreferenceUtil(HomeActivity.this);
        apiInterface = ApiUtils.getClient().create(DrService.class);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Firebase", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        tokenInput = new FcmToken();
                        tokenInput.setFcmToken(token);
                        isConnected();
                        sendToken();
                        // call api here and send token to server
                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Firebase#############", token);
                        //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

      getUserData();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       /* profilename = findViewById(R.id.profile_name);
        profilename.setText(profil_name);*/
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction(homeFragment);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

           /* Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);*/

        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction(homeFragment);

        } else if (id == R.id.notification) {



        } else if (id == R.id.mycampagins) {



        } else if (id == R.id.nav_refer_earn) {

            try {
                Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                // i.putExtra("TAB", "0");
                i.putExtra("FRAGMENT", "RepeatedDataFragment");
                i.putExtra("FRAGMENT_NAME", 0);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //startActivity( new Intent(HomeActivity.this,RepeatedDataActivity.class));

        }else if (id == R.id.nav_ewallet) {



        } else if (id == R.id.nav_logout) {

            FragmentManager manager = getSupportFragmentManager();
            Fragment frag = manager.findFragmentByTag(CONFIRM_TAG);
            if (frag != null) {
                manager.beginTransaction().remove(frag).commit();
            }

           alert_Box();

        } /*else if (id == R.id.nav_employee_dashboard) {


        }*//* else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       // NavigationView navigationView = findViewById(R.id.nav_view);
        drawer.closeDrawer(GravityCompat.START);

        /*View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.profile_namee);
        TextView navemail = (TextView) headerView.findViewById(R.id.offical_email_id);
        navUsername.setText(userdata.getName());
        navemail.setText(userdata.getOfficialEmail());*/

        return true;
    }


    private void FragmentTransaction(Fragment frag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, frag);
        fragmentTransaction.commit();

    }

    private void logout() {


        Log.d("LOGOUT***********", "Clear SharedPreferenceUtil");

    }


    private void alert_Box() {

        new AlertDialog.Builder(HomeActivity.this)
               // .setIcon(R.drawable.logo)
                .setTitle("FidelitusCorp")
                .setMessage("Are you sure you want to LogOut??")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        logout();
                        Log.d("LOGOUT***********", "Logging out");
                        /*Intent intent = new Intent(HomeActivity.this, SpalshActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Log.d("Internet is************", "Connected");
        } else {
            Log.d("Internet is************", "NOt Connected");
            Toast.makeText(HomeActivity.this, "Please Check Your Internet Connection!!", Toast.LENGTH_LONG).show();
        }
    }


   private void getUserData() {

        Call<GetUser> call = apiInterface.doGetUserDetails((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        call.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {

                if (response.isSuccessful()) {

                     //user = response.body().getUser();
                   // Helper.setLoggedInUserData(sharedPreferenceUtil, user);
                    Log.d("#######Sucess########", "Sucessssssssss");

                } else {
                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {

                Log.d("onFailureReaponse######", "posts not loaded from API");
                call.cancel();
            }
        });

    }
    private void sendToken() {

        apiInterface = ApiUtils.getClient().create(DrService.class);
        call = apiInterface.sendFbaseToken((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)), tokenInput);
        //call = apiInterface.sendFbaseToken((s), tokenInput);

        call.enqueue(new Callback<FcmTokenResponse>() {
            @Override
            public void onResponse(Call<FcmTokenResponse> call, Response<FcmTokenResponse> response) {


                if (response.isSuccessful()) {
                    //prog_login.setVisibility(View.GONE);
                    Log.d("################", "fcm token sent sucessfully");
                } else {
                    logout();
                    //Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                    // startActivity(intent);
                    Log.d("################", "fcm token not sent");
                }
            }

            @Override
            public void onFailure(Call<FcmTokenResponse> call, Throwable t) {
                call.cancel();

                Toast.makeText(HomeActivity.this, "Check Internent Connection!!", Toast.LENGTH_LONG).show();
                Log.d("faileddddddddddddddd", "send token");
                Log.d("faileddddddddddddddd", t.getMessage());
                Log.d("faileddddddddddddddd", t.toString());

            }
        });

    }
}
