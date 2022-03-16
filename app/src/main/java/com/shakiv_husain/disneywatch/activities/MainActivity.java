package com.shakiv_husain.disneywatch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.models.popular.MoviesResponse;
import com.shakiv_husain.disneywatch.util.Log;
import com.shakiv_husain.disneywatch.viewmodel.PopularMoviesViewModel;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PopularMoviesViewModel viewModel = new ViewModelProvider(this).get(PopularMoviesViewModel.class);

        viewModel.getPopularMovies(1).observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {

                if (moviesResponse != null) {
                    Log.d(TAG, moviesResponse.toString());
                }

            }
        });

    }
}