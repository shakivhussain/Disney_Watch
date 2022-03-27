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


    public LiveData<MoviesResponse> getUpcomingMovies(int page) {
        return moviesRepository.getUpcomingMovies(page);
    }


    public LiveData<MoviesResponse> getTopRated(int page) {
        return moviesRepository.getTopRated(page);
    }


    public LiveData<MoviesResponse> searchMovies(int page, String query) {
        return moviesRepository.searchMovies(page, query);
    }


    public LiveData<MoviesResponse> searchTvShows(int page, String query) {
        return moviesRepository.searchTvShows(page, query);
    }

    public LiveData<MoviesResponse> searchCollections(int page, String query) {
        return moviesRepository.searchCollections(page, query);
    }

}
