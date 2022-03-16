package com.shakiv_husain.disneywatch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.models.popular_movie.PopularMoviesResponse;
import com.shakiv_husain.disneywatch.util.Log;
import com.shakiv_husain.disneywatch.viewmodel.MovieDetailsViewModel;
import com.shakiv_husain.disneywatch.viewmodel.PopularMoviesViewModel;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getName();
    MovieDetailsViewModel movieDetailsViewMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PopularMoviesViewModel viewModel = new ViewModelProvider(this).get(PopularMoviesViewModel.class);

        movieDetailsViewMode = new ViewModelProvider(this).get(MovieDetailsViewModel.class);


        movieDetailsViewMode.getMovieDetails("278").observe(this, new Observer<MovieDetailsResponse>() {
            @Override
            public void onChanged(MovieDetailsResponse movieDetailsResponse) {
                if (movieDetailsResponse != null) {
                    Log.d(TAG, movieDetailsResponse.toString());
                }
            }
        });

        viewModel.getPopularMovies(1).observe(this, new Observer<PopularMoviesResponse>() {
            @Override
            public void onChanged(PopularMoviesResponse popularMoviesResponse) {

                if (popularMoviesResponse != null) {
//                    Log.d(TAG, popularMoviesResponse.toString());
                }

            }
        });

    }
}