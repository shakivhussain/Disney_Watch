package com.shakiv_husain.disneywatch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shakiv_husain.disneywatch.database.MovieDatabase;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;

import java.util.List;

import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private MovieDatabase movieDatabase;

    public WatchListViewModel(@NonNull Application application) {
        super(application);
        movieDatabase = MovieDatabase.getMovieDatabase(application);
    }

    public Flowable<List<MovieModel>> loadWatchList() {
        return movieDatabase.getMovieDao().getWatchList();
    }

}
