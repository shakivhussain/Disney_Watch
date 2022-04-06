package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.IS_WATCHLIST_UPDATED;
import static com.shakiv_husain.disneywatch.util.constants.AppConstants.MOVIE_MODEL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.WatchListAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivityWatchListBinding;
import com.shakiv_husain.disneywatch.listeners.WatchListListener;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;
import com.shakiv_husain.disneywatch.viewmodel.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchListListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel watchListViewModel;
    private WatchListAdapter watchListAdapter;
    private List<MovieModel> movieModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);

        initialization();

        loadWatchList();
    }

    private void initialization() {

        activityWatchListBinding.imageBack.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        movieModelList = new ArrayList<>();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (IS_WATCHLIST_UPDATED) {
            loadWatchList();
            IS_WATCHLIST_UPDATED = false;
        }
    }

    private void loadWatchList() {
        activityWatchListBinding.setIsLoading(true);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(movieModels -> {
                    if (movieModels != null) {
                        if (movieModels.size() > 0) {
                            movieModelList.clear();
                        }
                        activityWatchListBinding.setIsLoading(false);
                        movieModelList.addAll(movieModels);

                        watchListAdapter = new WatchListAdapter(this, movieModelList);
                        activityWatchListBinding.watchListRecyclerView.setAdapter(watchListAdapter);
                        activityWatchListBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
                        compositeDisposable.dispose();
                    }
                }));

    }

    @Override
    public void onMovieClicked(MovieModel movie) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(MOVIE_MODEL, movie);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void removeMovieFromWatchlist(MovieModel movie, int position) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.removeMovieFromWatchList(movie).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        movieModelList.remove(movie);
                        watchListAdapter.notifyItemRemoved(position);
                        watchListAdapter.notifyItemRangeChanged(position, watchListAdapter.getItemCount());
                        compositeDisposable.dispose();
                    }
                })
        );

    }
}