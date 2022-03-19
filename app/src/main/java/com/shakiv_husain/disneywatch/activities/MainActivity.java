package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.MovieAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivityMainBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;
import com.shakiv_husain.disneywatch.models.popular_movie.PopularMoviesResponse;
import com.shakiv_husain.disneywatch.util.Log;
import com.shakiv_husain.disneywatch.viewmodel.PopularMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListener {


    public static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding activityMainBinding;
    private PopularMoviesViewModel popularMoviesViewModel;
    private MovieAdapter movieAdapter;
    private List<MovieModel> movieList;
    int currentPage = 1;
    int totalPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialization();
    }

    private void initialization() {

        popularMoviesViewModel = new ViewModelProvider(this).get(PopularMoviesViewModel.class);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);
        activityMainBinding.mainRecyclerIew.setHasFixedSize(true);
        activityMainBinding.mainRecyclerIew.setAdapter(movieAdapter);
        activityMainBinding.mainRecyclerIew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (currentPage < totalPage) {
                    currentPage += 1;
                    Log.d(TAG, currentPage + " Total = " + totalPage);
                    getPopulerMoviesList();
                }
            }
        });


        getPopulerMoviesList();
    }

    private void getPopulerMoviesList() {

        toggleLoading();
        popularMoviesViewModel.getPopularMovies(currentPage).observe(this, new Observer<PopularMoviesResponse>() {
            @Override
            public void onChanged(PopularMoviesResponse popularMoviesResponse) {
                toggleLoading();


                if (popularMoviesResponse != null) {

                    totalPage = popularMoviesResponse.getTotalPages();
                    if (popularMoviesResponse.getMovies() != null) {

                        movieList.addAll(popularMoviesResponse.getMovies());
                        int oldMovieList = movieList.size();
                        movieAdapter.notifyItemRangeInserted(oldMovieList, movieList.size());
                    }

                }

            }
        });
    }


    private void toggleLoading() {

        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {

            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(ID, movieModel.getId());
        startActivity(intent);
    }
}