package com.shakiv_husain.disneywatch.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ActivityNoInternetConnectionBinding;
import com.shakiv_husain.disneywatch.util.network_connection.NetworkUtil;

public class NoInternetConnectionActivity extends AppCompatActivity {

    ActivityNoInternetConnectionBinding binding;
    Button btnRetry;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_internet_connection);

        initComponent();
    }


    private void initComponent() {

        imageView = findViewById(R.id.imageView);


        Glide.with(imageView.getContext())
                .load(R.raw.error)
                .transform(new FitCenter(), new RoundedCorners(28))
                .transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

        binding.setButtonText("Retry");
        binding.retryButton.setOnClickListener(view -> {
            if (NetworkUtil.mCheckNetworkStatus(NoInternetConnectionActivity.this)) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (NetworkUtil.mCheckNetworkStatus(NoInternetConnectionActivity.this)) {
            super.onBackPressed();
            Log.d("TAG", "onBackPressed: 1");
            finish();
        } else {
            return;
        }
    }

}