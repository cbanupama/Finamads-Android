package com.finamads.finamads.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.finamads.finamads.R;
import com.finamads.finamads.activities.CampaignDetailsActivity;
import com.finamads.finamads.model.AccpectCampaginResponse;
import com.finamads.finamads.model.AccpectCampaignInput;
import com.finamads.finamads.model.GetCurrentCampaign;
import com.finamads.finamads.model.RejectCampaignResponse;
import com.finamads.finamads.utilities.ApiUtils;
import com.finamads.finamads.utilities.Constants;
import com.finamads.finamads.utilities.DrService;
import com.finamads.finamads.utilities.SharedPreferenceUtil;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentCampaginAdapter extends RecyclerView.Adapter<CurrentCampaginAdapter.ViewHolder> implements Serializable {

    private List<GetCurrentCampaign> minsidejobCardlist;
    private CurrentCampaginAdapter.PostItemListener mItemListener;
    private Context mContext;
    DrService apiInterface;
    AccpectCampaignInput accpectCampaignInput;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView j_name, j_disc, j_outingtime;
        Button accpect, reject;
        CardView jobCardLayout;
        LinearLayout campaignLayout;


        public ViewHolder(View itemView, CurrentCampaginAdapter.PostItemListener postItemListener) {
            super(itemView);

            j_name = itemView.findViewById(R.id.jobcard_compony_title);
            j_disc = itemView.findViewById(R.id.jobcard_disc);
            j_outingtime = itemView.findViewById(R.id.jobcard_outingtime);
            accpect = itemView.findViewById(R.id.accpect_campaign);
            reject = itemView.findViewById(R.id.reject_campaign);
            campaignLayout = itemView.findViewById(R.id.campagin_layout);
            jobCardLayout = itemView.findViewById(R.id.cardview_jobcard_layout);
            sharedPreferenceUtil = new SharedPreferenceUtil(mContext);
        }


    }

    public CurrentCampaginAdapter(Context context, List<GetCurrentCampaign> categories) {
        minsidejobCardlist = categories;
        mContext = context;

    }

    @Override
    public CurrentCampaginAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.active_campaign_layout, parent, false);

        CurrentCampaginAdapter.ViewHolder viewHolder = new CurrentCampaginAdapter.ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CurrentCampaginAdapter.ViewHolder holder, final int position) {

        final GetCurrentCampaign item = minsidejobCardlist.get(position);

        //Log.d("#########size", String.valueOf(minsidejobCardlist.size()));
        holder.j_name.setText(item.getCampaign().getAdvertiserName());
        holder.j_disc.setText(item.getCampaign().getAdvertiserEmail());

        holder.accpect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accpectCampaignInput = new AccpectCampaignInput();
                accpectCampaignInput.setAdOfferId(item.getCampaign().getId());
                AccpectCampagin();


            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accpectCampaignInput = new AccpectCampaignInput();
                accpectCampaignInput.setAdOfferId(item.getCampaign().getId());
                RejectCampagin();


            }
        });

        holder.campaignLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, CampaignDetailsActivity.class);
                mContext.startActivity(i);


            }
        });


    }


    @Override
    public int getItemCount() {
        return minsidejobCardlist.size();
    }


    private GetCurrentCampaign getItem(int adapterPosition) {
        return minsidejobCardlist.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

    private void AccpectCampagin() {


        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<AccpectCampaginResponse> call = apiInterface.accpectCampaign((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)), accpectCampaignInput);
        call.enqueue(new Callback<AccpectCampaginResponse>() {
            @Override
            public void onResponse(Call<AccpectCampaginResponse> call, Response<AccpectCampaginResponse> response) {

                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    Log.d("@@@@@@@@@@@", String.valueOf(response.body().getCampaign().getId()));
                } else {

                    Toast.makeText(mContext, "Incorrect OTP!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccpectCampaginResponse> call, Throwable t) {
                call.cancel();

                Toast.makeText(mContext, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "OtpVerify activity api");
            }
        });

    }

    private void RejectCampagin() {


        apiInterface = ApiUtils.getClient().create(DrService.class);
        Call<RejectCampaignResponse> call = apiInterface.rejectCampaign((sharedPreferenceUtil.getStringPreference(Constants.KEY_API_KEY, null)), accpectCampaignInput);
        call.enqueue(new Callback<RejectCampaignResponse>() {
            @Override
            public void onResponse(Call<RejectCampaignResponse> call, Response<RejectCampaignResponse> response) {

                Log.d("@@@@@@@@@@@", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    Log.d("@@@@@@@@@@@", String.valueOf(response.body().getSuccess()));
                } else {

                    Toast.makeText(mContext, "Incorrect OTP!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RejectCampaignResponse> call, Throwable t) {
                call.cancel();

                Toast.makeText(mContext, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("faileddddddddddddddd", "OtpVerify activity api");
            }
        });

    }


}
