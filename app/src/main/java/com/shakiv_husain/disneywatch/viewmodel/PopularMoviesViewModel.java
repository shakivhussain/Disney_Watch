package com.shakiv_husain.disneywatch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shakiv_husain.disneywatch.models.popular.MoviesResponse;
import com.shakiv_husain.disneywatch.repositories.PopularMoviesRepository;

public class PopularMoviesViewModel extends ViewModel {


    private PopularMoviesRepository popularMoviesRepository;


    public PopularMoviesViewModel() {
        popularMoviesRepository = new PopularMoviesRepository();
    }


    public LiveData<MoviesResponse> getPopularMovies(int page) {
        return popularMoviesRepository.getPopularMovies(page);
    }

}
