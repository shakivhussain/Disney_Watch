package com.shakiv_husain.disneywatch.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding activitySignInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);


        initialization();

    }

    private void initialization() {
        activitySignInBinding.buttonSignIn.setOnClickListener(view -> {

        });

        activitySignInBinding.buttonSignUp.setOnClickListener(view -> onBackPressed());

    }
}