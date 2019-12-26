package com.finamads.finamads.adapters;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finamads.finamads.R;
import com.finamads.finamads.activities.KycActivity;
import com.finamads.finamads.model.GetVehicleType;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 1/1/2018.
 */

public class VehicleTypeAdapter extends RecyclerView.Adapter<VehicleTypeAdapter.ViewHolder>{

    private static final String TAG = "VehicleTypeAdapter";

    private ArrayList<GetVehicleType> getVehicleTypes = new ArrayList<>();
    private Context mContext;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public VehicleTypeAdapter(Context context, ArrayList<GetVehicleType> imageNames) {
        getVehicleTypes = imageNames;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(getVehicleTypes.get(position).getImages().get(0).getUrl())
                .into(holder.image);

        holder.imageName.setText(getVehicleTypes.get(position).getName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + getVehicleTypes.get(position).getId());

                Toast.makeText(mContext, getVehicleTypes.get(position).getName(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("vehicle_id", String.valueOf(getVehicleTypes.get(position).getId()) );
                editor.apply();
                Intent intent = new Intent(mContext, KycActivity.class);
                intent.putExtra("vehicle_id", String.valueOf(getVehicleTypes.get(position).getId()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getVehicleTypes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        //CircleImageView image;
        ImageView image;
        TextView imageName;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}















