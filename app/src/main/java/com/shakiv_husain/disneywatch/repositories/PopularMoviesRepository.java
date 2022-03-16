package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.constants.ApiConstants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.popular_movie.PopularMoviesResponse;
import com.shakiv_husain.disneywatch.network.ApiClient;
import com.shakiv_husain.disneywatch.network.ApiServices;
import com.shakiv_husain.disneywatch.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesRepository {

    private static final String TAG = PopularMoviesRepository.class.getName();


    private ApiServices apiServices;

    public PopularMoviesRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<PopularMoviesResponse> getPopularMovies(int page) {
        MutableLiveData<PopularMoviesResponse> popularMovies = new MutableLiveData<>();

        apiServices.getPopularMovies(page, API_KEY).enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesResponse> call, @NonNull Response<PopularMoviesResponse> response) {
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
            public void onFailure(@NonNull Call<PopularMoviesResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                popularMovies.postValue(null);
            }
        });

        return popularMovies;
    }
}
