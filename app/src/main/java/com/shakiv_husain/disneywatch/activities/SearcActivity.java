package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.ID;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.VerticalMovieAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivitySearcBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;
import com.shakiv_husain.disneywatch.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearcActivity extends AppCompatActivity implements MovieListener {

    private ActivitySearcBinding searcBinding;
    private MoviesViewModel moviesViewModel;
    private int currentPageMovies = 1;
    private int currentPageTvShow = 1;
    private List<MovieModel> movieModelList = new ArrayList<>();
    private List<MovieModel> tvShowsModelList = new ArrayList<>();
    private VerticalMovieAdapter movieAdaper;
    private VerticalMovieAdapter tvShowsAdapter;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searcBinding = DataBindingUtil.setContentView(this, R.layout.activity_searc);

        initialize();

    }

    private void initialize() {

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);


        searcBinding.backButton.setOnClickListener(view -> onBackPressed());

        movieAdaper = new VerticalMovieAdapter(movieModelList, this);
        searcBinding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        searcBinding.moviesRecyclerView.setAdapter(movieAdaper);

        // Tv Shows
        tvShowsAdapter = new VerticalMovieAdapter(tvShowsModelList, this);
        searcBinding.tvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        searcBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);

        searcBinding.inputSearch.requestFocus();
        searcBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPageMovies = 1;
                                currentPageTvShow = 1;
                                setSearchTvShows(editable.toString());
                                setSearchMovies(editable.toString());
                            });
                        }
                    }, 500);
                } else {

                    tvShowsModelList.clear();
                    movieModelList.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                    movieAdaper.notifyDataSetChanged();
                }

            }
        });

        searcBinding.moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searcBinding.moviesRecyclerView.canScrollVertically(1)) {
                    currentPageMovies += 1;
                    setSearchMovies(searcBinding.inputSearch.getText().toString());
                }
            }
        });

        searcBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searcBinding.moviesRecyclerView.canScrollVertically(1)) {
                    currentPageTvShow += 1;
                    setSearchTvShows(searcBinding.inputSearch.getText().toString());
                }
            }
        });

    }

    private void setSearchTvShows(String query) {

        moviesViewModel.searchTvShows(currentPageTvShow, query).observe(this, moviesResponse -> {
            if (moviesResponse != null) {
                if (moviesResponse.getMovies() != null) {
                    int count = tvShowsModelList.size();
                    tvShowsModelList.addAll(moviesResponse.getMovies());
                    tvShowsAdapter.notifyItemRangeChanged(count, tvShowsModelList.size());
                }
            }
        });


    }

    private void setSearchMovies(String query) {

        searcBinding.progresView.setVisibility(View.VISIBLE);
        moviesViewModel.searchMovies(currentPageMovies, query).observe(this, moviesResponse -> {
            if (moviesResponse != null) {

                searcBinding.recyclerViewConatainer.setVisibility(View.VISIBLE);
                searcBinding.emptyPlaceHolder.setVisibility(View.GONE);
                searcBinding.progresView.setVisibility(View.GONE);

                if (moviesResponse.getMovies() != null) {
                    int count = movieModelList.size();
                    movieModelList.addAll(moviesResponse.getMovies());
                    movieAdaper.notifyItemRangeChanged(count, movieModelList.size());
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onTvShowClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(ID, movieModel.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}