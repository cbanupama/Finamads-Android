package com.finamads.finamads.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.finamads.finamads.BuildConfig;
import com.finamads.finamads.R;
import com.finamads.finamads.adapters.VehicleTypeAdapter;
import com.finamads.finamads.model.AddVehicleInput;
import com.finamads.finamads.model.GetVehicleType;
import com.finamads.finamads.model.GetVehiclesResponse;
import com.finamads.finamads.model.LoginResponse;
import com.finamads.finamads.utilities.Api;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Client;
import com.finamads.finamads.utilities.CompressImageSize;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.Helper;
import com.finamads.finamads.utilities.SharedPreferenceUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finamads.finamads.adapters.VehicleTypeAdapter.MY_PREFS_NAME;

public class KycActivity extends AppCompatActivity implements View.OnClickListener {

    private String mImageFileLocation = "";
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int CAMERA_PIC_REQUEST = 1111;
    private static final String TAG = KycActivity.class.getSimpleName();
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private String mediaPath;
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    private String postPath;
    ImageView rcFont, rcBack, vpFront, vpBack, dlFront, dlBack;
    Button submitKyc;
    public static ImageView img;
    String type, btn_type;
    Button submitRc, submitDl, submitVp;
    ArrayList<String> upload_rc_files = new ArrayList<>();
    ArrayList<String> upload_dl_files = new ArrayList<>();
    ArrayList<String> upload_vp_files = new ArrayList<>();
    private SharedPreferenceUtil sharedPreferenceUtil;
    LinearLayout layoutprograss;
    String venName;
    LoginResponse loginResponse;
    Call<AddVehicleInput> call;
    DrService apiInterface;
    GetVehiclesResponse getVehiclesResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);

        rcFont = findViewById(R.id.rc_front);
        rcBack = findViewById(R.id.rc_back);
        vpBack = findViewById(R.id.vp_back);
        vpFront = findViewById(R.id.vp_front);
        dlBack = findViewById(R.id.dl_back);
        dlFront = findViewById(R.id.dl_front);
        submitKyc = findViewById(R.id.submit_kyc);
        submitRc = findViewById(R.id.submit_rc);
        submitDl = findViewById(R.id.submit_dl);
        submitVp = findViewById(R.id.submit_vp);
        sharedPreferenceUtil = new SharedPreferenceUtil(KycActivity.this);
        layoutprograss = findViewById(R.id.upload_progress_layout);

        apiInterface = ApiUtils.getClient().create(DrService.class);
        sharedPreferenceUtil = new SharedPreferenceUtil(KycActivity.this);
        getVehiclesResponse = new GetVehiclesResponse();
        getVehicle();
        loginResponse = Helper.getLoggedInUserData(sharedPreferenceUtil);


       /* if (getVehiclesResponse.getId() != null) {


        }*/


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        venName = prefs.getString("vehicle_id", "No name defined");//"No name defined" is the default value.


        Log.d("prefs_vehicle_id###", venName);


        rcFont.setOnClickListener(this);
        rcBack.setOnClickListener(this);
        vpBack.setOnClickListener(this);
        vpFront.setOnClickListener(this);
        dlBack.setOnClickListener(this);
        dlFront.setOnClickListener(this);
        submitKyc.setOnClickListener(this);
        submitRc.setOnClickListener(this);
        submitDl.setOnClickListener(this);
        submitVp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rc_front:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    img = rcFont;
                    type = "rcFont";
                    captureImage();
                }
                break;

            case R.id.rc_back:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    img = rcBack;
                    type = "rcBack";
                    captureImage();
                }
                break;

            case R.id.dl_front:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    img = dlFront;
                    type = "dlFront";
                    captureImage();
                }
                break;

            case R.id.dl_back:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    img = dlBack;
                    type = "dlBack";
                    captureImage();
                }
                break;

            case R.id.vp_front:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    img = vpFront;
                    type = "vpFront";
                    captureImage();
                }
                break;

            case R.id.vp_back:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    img = vpBack;
                    type = "vpBack";
                    captureImage();
                }
                break;

            case R.id.submit_rc:
                btn_type = "submit_rc";

                if (upload_rc_files.size() >= 1) {

                    Log.d("#######upload_rc_files", "############");
                    layoutprograss.setVisibility(View.VISIBLE);
                    MultipartBody.Builder rc_builder = new MultipartBody.Builder();
                    rc_builder.setType(MultipartBody.FORM);
                    rc_builder.addFormDataPart("vehicle_type_id", venName);

                    File f_rcfile = new File(upload_rc_files.get(0));
                    rc_builder.addFormDataPart("rc_front", f_rcfile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f_rcfile));

                    File b_rcfile = new File(upload_rc_files.get(1));
                    rc_builder.addFormDataPart("rc_back", b_rcfile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), b_rcfile));

                    if (getVehiclesResponse.getId() != null) {

                        rc_builder.addFormDataPart("_method", "PUT");
                    }

                    MultipartBody rc_requestBody = rc_builder.build();
                    isConnected();
                    submitImagesForm(rc_requestBody);

                } else {

                    Toast.makeText(KycActivity.this, "Please select file to upload", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.submit_dl:
                btn_type = "submit_dl";
                if (upload_dl_files.size() >= 1) {
                    Log.d("#######upload_dl_files", "############");
                    layoutprograss.setVisibility(View.VISIBLE);
                    MultipartBody.Builder dl_builder = new MultipartBody.Builder();
                    dl_builder.setType(MultipartBody.FORM);
                    dl_builder.addFormDataPart("vehicle_type_id", venName);
                    if (getVehiclesResponse.getId() != null) {
                        Log.d("##########_method", "PUT");
                        dl_builder.addFormDataPart("_method", "PUT");
                    }
                    File f_dlfile = new File(upload_dl_files.get(0));
                    Log.d("#######f_dlfile", upload_dl_files.get(0));
                    dl_builder.addFormDataPart("dl_front", f_dlfile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f_dlfile));

                    File b_dlfile = new File(upload_dl_files.get(1));
                    Log.d("#######b_dlfile", upload_dl_files.get(1));
                    dl_builder.addFormDataPart("dl_back", b_dlfile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), b_dlfile));

                    MultipartBody dl_requestBody = dl_builder.build();
                    isConnected();
                    submitImagesForm(dl_requestBody);
                } else {

                    Toast.makeText(KycActivity.this, "Please select file to upload", Toast.LENGTH_LONG).show();
                }

                break;


            case R.id.submit_vp:
                btn_type = "submit_vp";
                if (upload_vp_files.size() >= 1) {
                    Log.d("#######upload_vp_files", "############");

                    layoutprograss.setVisibility(View.VISIBLE);
                    MultipartBody.Builder vp_builder = new MultipartBody.Builder();
                    vp_builder.setType(MultipartBody.FORM);
                    vp_builder.addFormDataPart("vehicle_type_id", venName);
                    vp_builder.addFormDataPart("_method", "PUT");
                    File f_vpfile = new File(upload_vp_files.get(0));
                    Log.d("#######f_vpfile", upload_vp_files.get(0));
                    vp_builder.addFormDataPart("vh_front", f_vpfile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f_vpfile));

                    File b_vpfile = new File(upload_vp_files.get(1));
                    Log.d("#######b_vpfile", upload_vp_files.get(1));

                   if (getVehiclesResponse.getId() != null) {

                        vp_builder.addFormDataPart("_method","PUT");
                    }
                    MultipartBody vp_requestBody = vp_builder.build();
                    isConnected();
                    submitImagesForm(vp_requestBody);
                } else {

                    Toast.makeText(KycActivity.this, "Please select file to upload", Toast.LENGTH_LONG).show();
                }


                break;

            case R.id.submit_kyc:
                Intent intent = new Intent(KycActivity.this, UserLocationActivity.class);
                startActivity(intent);
                break;


        }
    }

    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            // callCameraApplicationIntent.putExtra("android.intent.extras.CAMERA_FACING_BACK", 0);
            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // intent.putExtra("android.intent.extras.CAMERA_FACING_BACK", 0);
            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);

        }

    }

    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/FidelitusReception");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    img.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();

                    postPath = mediaPath;
                }


            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(img);
                    postPath = mImageFileLocation;

                } else {

                    Glide.with(this).load(fileUri).into(img);
                    postPath = fileUri.getPath();

                }
                switch (type) {
                    case "rcFont":
                        upload_rc_files.add(0, CompressImageSize.compressImage(postPath, KycActivity.this));
                        break;


                    case "rcBack":
                        upload_rc_files.add(1, CompressImageSize.compressImage(postPath, KycActivity.this));
                        break;

                    case "dlFront":
                        Log.d("##############dlFront", CompressImageSize.compressImage(postPath, KycActivity.this));
                        upload_dl_files.add(0, CompressImageSize.compressImage(postPath, KycActivity.this));
                        break;


                    case "dlBack":
                        Log.d("##############dlBack", CompressImageSize.compressImage(postPath, KycActivity.this));
                        upload_dl_files.add(1, CompressImageSize.compressImage(postPath, KycActivity.this));
                        break;


                    case "vpFront":
                        Log.d("##############vpFront", CompressImageSize.compressImage(postPath, KycActivity.this));
                        upload_vp_files.add(0, CompressImageSize.compressImage(postPath, KycActivity.this));
                        break;

                    case "vpBack":
                        Log.d("##############vpBack", CompressImageSize.compressImage(postPath, KycActivity.this));
                        upload_vp_files.add(1, CompressImageSize.compressImage(postPath, KycActivity.this));
                        break;

                }


            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast toast = Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create"
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }



    /*@NonNull
    private RequestBody createPartFromString(String descriptionString) {
        if (descriptionString == null)
            return RequestBody.create(MultipartBody.FORM, "");
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);

    }
*/

    public void isConnected() {

        boolean st;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Log.d("Internet is************", "Connected");
            st = true;
        } else {
            Log.d("Internet is************", "NOt Connected");
            Toast toast = Toast.makeText(KycActivity.this, "Please check your Internet Connection!!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            st = false;
        }
    }

    private void submitImagesForm(MultipartBody requestBody) {


        Api api = Client.api();
        // prepare headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", (sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        headers.put("Accept", "application/json");
        if (getVehiclesResponse.getId() == null) {
            call = api.uploadPostFiles(headers, requestBody);
        } else {
            call = api.uploadPutFiles(headers, requestBody, getVehiclesResponse.getId());
        }

        call.enqueue(new Callback<AddVehicleInput>() {
            @Override
            public void onResponse(Call<AddVehicleInput> call, Response<AddVehicleInput> response) {
                System.out.println("response received");
                System.out.println(response.body());
                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    layoutprograss.setVisibility(View.GONE);
                    Toast.makeText(KycActivity.this, "Images Uploaded Successfully!!", Toast.LENGTH_LONG).show();
                    //submitRc.setBackgroundResource(R.drawable.buttn_bg);

                    switch (btn_type) {
                        case "submit_rc":
                            Log.d("#######submit_dl", "submit_dl");
                            if (response.body().getRcBack() != null && response.body().getRcFront() != null) {
                                //Log.d("#######submit_rc","submit_rc");
                                submitRc.setBackgroundResource(R.drawable.buttn_bg);
                                submitRc.setText("Uploaded RC");
                            }

                            break;


                        case "submit_dl":
                            Log.d("#######submit_dl", "submit_dl");
                            if (response.body().getDlBack() != null && response.body().getDlFront() != null) {
                                // Log.d("#######submit_dl","submit_dl");
                                submitDl.setBackgroundResource(R.drawable.buttn_bg);
                                submitDl.setText("Uploaded DL");
                            }
                            break;

                        case "submit_vp":
                            Log.d("#######submit_vp", "submit_vp");
                            if (response.body().getVhBack() != null && response.body().getVhFront() != null) {
                                // Log.d("#######submit_vp","submit_vp");
                                submitVp.setBackgroundResource(R.drawable.buttn_bg);
                                submitVp.setText("Uploaded Vehicle Picture");
                            }
                            break;

                    }

                   getVehicle();

                } else {
                    layoutprograss.setVisibility(View.GONE);
                    Toast.makeText(KycActivity.this, "Please Check internet connection and try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddVehicleInput> call, Throwable t) {
                System.out.println("error received");
                System.out.println(t.getMessage());
                System.out.println(t.toString());
                call.cancel();
                //layoutprograss.setVisibility(View.GONE);
                Toast.makeText(KycActivity.this, "Something Went Worng", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "KycActivity");
            }
        });
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    private void getVehicle() {

        Call<ArrayList<GetVehiclesResponse>> call = apiInterface.getVehicle((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)));
        call.enqueue(new Callback<ArrayList<GetVehiclesResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<GetVehiclesResponse>> call, Response<ArrayList<GetVehiclesResponse>> response) {

                if (response.isSuccessful()) {

                    if (!response.body().isEmpty()) {
                        getVehiclesResponse = response.body().get(0);
                        Log.d("getVehiclesResponse###", String.valueOf(getVehiclesResponse.getId()));

                        if (getVehiclesResponse.getVhFront() != null) {

                            Glide.with(KycActivity.this).load(getVehiclesResponse.getVhFront()).into(vpFront);
                        }

                        if (getVehiclesResponse.getVhBack() != null) {

                            Glide.with(KycActivity.this).load(getVehiclesResponse.getVhBack()).into(vpBack);
                        }

                        if (getVehiclesResponse.getDlFront() != null) {

                            Glide.with(KycActivity.this).load(getVehiclesResponse.getDlFront()).into(dlFront);
                        }

                        if (getVehiclesResponse.getDlBack() != null) {

                            Glide.with(KycActivity.this).load(getVehiclesResponse.getDlBack()).into(dlBack);
                        }

                        if (getVehiclesResponse.getRcFront() != null) {

                            Glide.with(KycActivity.this).load(getVehiclesResponse.getRcFront()).into(rcFont);
                        }

                        if (getVehiclesResponse.getRcBack() != null) {

                            Glide.with(KycActivity.this).load(getVehiclesResponse.getRcBack()).into(rcBack);
                        }

                        if ((getVehiclesResponse.getDlBack() != null) && (getVehiclesResponse.getDlFront() != null)) {

                            //submitDl.setEnabled(false);
                            submitDl.setText("Uploaded DL");
                            submitDl.setBackgroundResource(R.drawable.buttn_bg);
                        }

                        if ((getVehiclesResponse.getRcBack() != null) && (getVehiclesResponse.getRcFront() != null)) {
                            //submitRc.setEnabled(false);
                            submitRc.setText("Uploaded RC");
                            submitRc.setBackgroundResource(R.drawable.buttn_bg);
                        }

                        if ((getVehiclesResponse.getVhBack() != null) && (getVehiclesResponse.getVhFront() != null)) {
                            //submitVp.setEnabled(false);
                            submitVp.setText("Uploaded Vehicle Picture");
                            submitVp.setBackgroundResource(R.drawable.buttn_bg);
                        }


                    }
                } else {

                    Log.d("#######failed########", "Faillllllllllllllllll");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetVehiclesResponse>> call, Throwable t) {

                Log.d("onFailureReaponse######", "On Failure API Reaponse ");
                call.cancel();
            }
        });

    }
}
