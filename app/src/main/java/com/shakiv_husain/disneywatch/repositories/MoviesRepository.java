package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.movie.MoviesResponse;
import com.shakiv_husain.disneywatch.network.ApiClient;
import com.shakiv_husain.disneywatch.network.ApiServices;
import com.shakiv_husain.disneywatch.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static final String TAG = MoviesRepository.class.getName();


    private ApiServices apiServices;

    public MoviesRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<MoviesResponse> getPopularMovies(int page) {
        MutableLiveData<MoviesResponse> popularMovies = new MutableLiveData<>();

        apiServices.getPopularMovies(page, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                Log.d(TAG, "onResponse: Api Call ::" + call.request().url());
                if (response.isSuccessful()) {
                    popularMovies.postValue(response.body());
                } else {
                    assert response.errorBody() != null;
                    Log.i(TAG, "Error" + response.toString());
                    popularMovies.postValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                popularMovies.postValue(null);
            }
        });

        return popularMovies;
    }

    public LiveData<MoviesResponse> getSimilarMovies(String movie_id, int page) {

        MutableLiveData<MoviesResponse> similarMovies = new MutableLiveData<>();

        apiServices.getSimilarMovies(movie_id, page, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                if (response.isSuccessful()) {

                    similarMovies.postValue(response.body());
                } else {
                    Log.e(TAG, " similarMovies Error : " + response.toString());
                    similarMovies.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

                Log.e(TAG, "Response : " + t.getMessage() + "  ");
            }
        });

        return similarMovies;
    }

    public LiveData<MoviesResponse> getUpcomingMovies(int page) {

        MutableLiveData<MoviesResponse> upcomingMovies = new MutableLiveData<>();

        apiServices.getUpcomingMovies(page, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    upcomingMovies.postValue(response.body());
                } else {
                    Log.e(TAG, " UpcomingMovies Error 1 : " + response.toString());
                    upcomingMovies.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

                Log.e(TAG, " UpcomingMovies Error : " + t.getMessage());
            }
        });

        return upcomingMovies;
    }

    public LiveData<MoviesResponse> getTopRated(int page) {

        MutableLiveData<MoviesResponse> topRatedMovies = new MutableLiveData<>();

        apiServices.getTopRatedMovies(page, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    topRatedMovies.postValue(response.body());
                } else {
                    Log.e(TAG, response.toString());
                    topRatedMovies.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                topRatedMovies.postValue(null);
            }
        });
        return topRatedMovies;
    }

    public LiveData<MoviesResponse> searchMovies(int page, String query) {
        MutableLiveData<MoviesResponse> movies = new MutableLiveData<>();
        apiServices.searchMovies(page, query, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                if (response.isSuccessful()) {
                    movies.postValue(response.body());
                } else {
                    Log.e(TAG, response.toString());
                    movies.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                movies.postValue(null);
            }
        });
        return movies;
    }

    public LiveData<MoviesResponse> searchTvShows(int page, String query) {

        MutableLiveData<MoviesResponse> tvShows = new MutableLiveData<>();

        apiServices.searchTvShows(page, query, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG, call.request().url().toString());
                if (response.isSuccessful()) {
                    tvShows.postValue(response.body());
                } else {
                    tvShows.postValue(null);
                    Log.e(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
        return tvShows;
    }


    public LiveData<MoviesResponse> searchCollections(int page, String query) {

        MutableLiveData<MoviesResponse> collections = new MutableLiveData<>();


        apiServices.searchCollections(page, query, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    collections.postValue(response.body());
                } else {
                    Log.e(TAG, response.toString());
                    collections.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
        return collections;
    }


}
