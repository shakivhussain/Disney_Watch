package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.ID;
import static com.shakiv_husain.disneywatch.util.constants.AppConstants.MOVIE_MODEL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
import com.shakiv_husain.disneywatch.models.movie.MovieModel;
import com.shakiv_husain.disneywatch.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearcActivity extends AppCompatActivity implements MovieListener {

    private ActivitySearcBinding searcBinding;
    private MoviesViewModel moviesViewModel;
    private int currentPageMovies = 1, currentPageTvShow = 1, currentPageCollection = 1;
    private List<MovieModel> movieModelList;
    private List<MovieModel> tvShowsModelList;
    private List<MovieModel> collections;
    private VerticalMovieAdapter movieAdaper, tvShowsAdapter, collectionAdapter;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searcBinding = DataBindingUtil.setContentView(this, R.layout.activity_searc);

        initialize();

    }

    private void initialize() {

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        movieModelList = new ArrayList<>();
        tvShowsModelList = new ArrayList<>();
        collections = new ArrayList<>();

        searcBinding.backButton.setOnClickListener(view -> onBackPressed());

        movieAdaper = new VerticalMovieAdapter(movieModelList, this);
        searcBinding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        searcBinding.moviesRecyclerView.setAdapter(movieAdaper);

        // Tv Shows
        tvShowsAdapter = new VerticalMovieAdapter(tvShowsModelList, this);
        searcBinding.tvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        searcBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);

        // Collections
        collectionAdapter = new VerticalMovieAdapter(collections, this);
        searcBinding.collectionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        searcBinding.collectionsRecyclerView.setAdapter(collectionAdapter);

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
                                currentPageCollection = 1;
                                setSearchTvShows(editable.toString());
                                setSearchMovies(editable.toString());
                                setSearchCollection(editable.toString());
                            });
                        }
                    }, 500);
                } else {
                    tvShowsModelList.clear();
                    movieModelList.clear();
                    collections.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                    movieAdaper.notifyDataSetChanged();
                    collectionAdapter.notifyDataSetChanged();
                }

            }
        });

        searcBinding.inputSearch.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(view);
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


        searcBinding.collectionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searcBinding.collectionsRecyclerView.canScrollVertically(1)) {
                    currentPageCollection += 1;
                    setSearchCollection(searcBinding.inputSearch.getText().toString());
                }
            }
        });


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setSearchCollection(String query) {
        moviesViewModel.searchCollections(currentPageCollection, query).observe(this, moviesResponse -> {
            try {
                if (moviesResponse != null) {
                    if (moviesResponse.getMovies() != null) {
                        if (moviesResponse.getMovies() != null) {
                            int count = collections.size();
                            collections.addAll(moviesResponse.getMovies());
                            collectionAdapter.notifyItemRangeChanged(count, collections.size());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setSearchTvShows(String query) {

        moviesViewModel.searchTvShows(currentPageTvShow, query).observe(this, moviesResponse -> {
            try {
                if (moviesResponse != null) {
                    if (moviesResponse.getMovies() != null) {
                        int count = tvShowsModelList.size();
                        tvShowsModelList.addAll(moviesResponse.getMovies());
                        tvShowsAdapter.notifyItemRangeChanged(count, tvShowsModelList.size());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    private void setSearchMovies(String query) {
        moviesViewModel.searchMovies(currentPageMovies, query).observe(this, moviesResponse -> {
            try {
                if (moviesResponse != null) {
                    searcBinding.recyclerViewConatainer.setVisibility(View.VISIBLE);
                    searcBinding.emptyPlaceHolder.setVisibility(View.GONE);

                    if (moviesResponse.getMovies() != null) {
                        int count = movieModelList.size();
                        movieModelList.addAll(moviesResponse.getMovies());
                        movieAdaper.notifyItemRangeChanged(count, movieModelList.size());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        intent.putExtra(MOVIE_MODEL, movieModel);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}