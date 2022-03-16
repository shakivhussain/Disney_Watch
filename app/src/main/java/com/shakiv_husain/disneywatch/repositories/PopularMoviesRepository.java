package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.constants.ApiConstants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.popular.MoviesResponse;
import com.shakiv_husain.disneywatch.network.ApiClient;
import com.shakiv_husain.disneywatch.network.ApiServices;
import com.shakiv_husain.disneywatch.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesRepository {

    private static final String TAG = PopularMoviesRepository.class.getName();


    private ApiServices apiServices;

    public PopularMoviesRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<MoviesResponse> getPopularMovies(int page) {
        MutableLiveData<MoviesResponse> popularMovies = new MutableLiveData<>();

        apiServices.getPopularMovies(page, API_KEY).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                try {
                    Log.d(TAG, "onResponse: Api Call ::" + call.request().url());
                    if (response.isSuccessful()) {
                        popularMovies.postValue(response.body());
                    } else {
                        assert response.errorBody() != null;
                        Log.i(TAG, response.errorBody().string());
                        popularMovies.postValue(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return popularMovies;
    }
}
