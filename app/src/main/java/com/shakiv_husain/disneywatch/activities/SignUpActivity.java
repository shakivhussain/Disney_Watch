package com.shakiv_husain.disneywatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);


        initialization();


    }

    private void initialization() {


        activitySignUpBinding.buttonSignIn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finish();
        });

        activitySignUpBinding.buttonSignUp.setOnClickListener(view -> {
            signUp();
        });
    }

    private void signUp() {
        Toast.makeText(this, "Sign-Up", Toast.LENGTH_SHORT).show();
    }
}