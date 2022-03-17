package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.API_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.network.ApiClient;
import com.shakiv_husain.disneywatch.network.ApiServices;
import com.shakiv_husain.disneywatch.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {


    private static final String TAG = MovieDetailsRepository.class.getName();
    private ApiServices apiServices;

    public MovieDetailsRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }


    public LiveData<MovieDetailsResponse> getMovieDetails(String movieId) {

        MutableLiveData<MovieDetailsResponse> movieDetailsResponseMutableLiveData = new MutableLiveData<>();

        apiServices.getMovieDetails(movieId, API_KEY).enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.isSuccessful()) {
                    movieDetailsResponseMutableLiveData.postValue(response.body());
                } else {
                    Log.e(TAG, "Error" + response.errorBody());
                    movieDetailsResponseMutableLiveData.postValue(null);
                }
                Log.d(TAG, "Api : " + call.request().url());
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                movieDetailsResponseMutableLiveData.postValue(null);
                Log.e(TAG, t.getMessage());

            }
        });

        return movieDetailsResponseMutableLiveData;
    }

}
