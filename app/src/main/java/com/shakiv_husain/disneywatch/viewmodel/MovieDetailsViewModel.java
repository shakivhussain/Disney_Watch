package com.shakiv_husain.disneywatch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.repositories.MovieDetailsRepository;

public class MovieDetailsViewModel extends ViewModel {

    private MovieDetailsRepository movieDetailsRepository;

    public MovieDetailsViewModel() {
        movieDetailsRepository = new MovieDetailsRepository();
    }

    public LiveData<MovieDetailsResponse> getMovieDetails(String movieId) {
        return movieDetailsRepository.getMovieDetails(movieId);
    }
}
