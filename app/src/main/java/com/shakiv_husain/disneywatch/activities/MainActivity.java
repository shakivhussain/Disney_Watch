package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.ID;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.MovieAdapter;
import com.shakiv_husain.disneywatch.adapter.UpcomingMovieAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivityMainBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;
import com.shakiv_husain.disneywatch.models.popular_movie.MoviesResponse;
import com.shakiv_husain.disneywatch.util.Log;
import com.shakiv_husain.disneywatch.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListener {


    public static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding activityMainBinding;
    private MoviesViewModel moviesViewModel;
    private MovieAdapter movieAdapter;
    private List<MovieModel> movieList;
    private Handler sliderHandler = new Handler();
    int currentPage = 1;
    int totalPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialization();
    }

    private void initialization() {

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
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
                    setPopulerMoviesList();
                }
            }
        });


        setPopulerMoviesList();
        setUpcomingMovies();
    }

    private void setUpcomingMovies() {


        moviesViewModel.getUpcomingMovies(1).observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if (moviesResponse != null) {

                    if (moviesResponse.getMovies() != null && moviesResponse.getMovies().size() > 0) {
                        setUpcomingMoviesAdapter(moviesResponse);
                    }
                }
            }
        });


    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            activityMainBinding.upcomingMoviesViewPager.setCurrentItem(activityMainBinding.upcomingMoviesViewPager.getCurrentItem() + 1);
        }
    };

    private void setUpcomingMoviesAdapter(MoviesResponse moviesResponse) {

        activityMainBinding.upcomingMoviesViewPager.setAdapter(new UpcomingMovieAdapter(this, moviesResponse.getMovies() ,activityMainBinding.upcomingMoviesViewPager));
        activityMainBinding.upcomingMoviesViewPager.setClipToPadding(false);
        activityMainBinding.upcomingMoviesViewPager.setClipChildren(false);
        activityMainBinding.upcomingMoviesViewPager.setOffscreenPageLimit(3);
        activityMainBinding.upcomingMoviesViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);



        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        activityMainBinding.upcomingMoviesViewPager.setPageTransformer(compositePageTransformer);

        activityMainBinding.upcomingMoviesViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

    }

    private void setPopulerMoviesList() {

        toggleLoading();
        moviesViewModel.getPopularMovies(currentPage).observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                toggleLoading();


                if (moviesResponse != null) {

                    totalPage = moviesResponse.getTotalPages();
                    if (moviesResponse.getMovies() != null) {

                        movieList.addAll(moviesResponse.getMovies());
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