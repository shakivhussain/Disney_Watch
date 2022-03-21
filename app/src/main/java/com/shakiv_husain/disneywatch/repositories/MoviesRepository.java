package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.popular_movie.MoviesResponse;
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
                    Log.i(TAG, "Error" + response.errorBody());
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
                    Log.e(TAG, "onResponse: " + response.errorBody());
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

}
