package com.shakiv_husain.disneywatch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shakiv_husain.disneywatch.models.popular_movie.MoviesResponse;
import com.shakiv_husain.disneywatch.repositories.MoviesRepository;

public class MoviesViewModel extends ViewModel {


    private MoviesRepository moviesRepository;


    public MoviesViewModel() {
        moviesRepository = new MoviesRepository();
    }


    public LiveData<MoviesResponse> getPopularMovies(int page) {
        return moviesRepository.getPopularMovies(page);
    }


    public LiveData<MoviesResponse> getSimilarMovies(String movie_id, int page) {
        return moviesRepository.getSimilarMovies(movie_id, page);
    }
}
