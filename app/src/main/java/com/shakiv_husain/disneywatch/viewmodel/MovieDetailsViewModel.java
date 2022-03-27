package com.shakiv_husain.disneywatch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shakiv_husain.disneywatch.database.MovieDatabase;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;
import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.repositories.MovieDetailsRepository;

import io.reactivex.Completable;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MovieDetailsRepository movieDetailsRepository;
    private MovieDatabase movieDatabase;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieDetailsRepository = new MovieDetailsRepository();
        movieDatabase = MovieDatabase.getMovieDatabase(application);
    }

    public LiveData<MovieDetailsResponse> getMovieDetails(String movieId) {
        return movieDetailsRepository.getMovieDetails(movieId);
    }

    public Completable addToWatchList(MovieModel movieModel) {
        return movieDatabase.getMovieDao().addToWatchList(movieModel);
    }

}
