package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.API_KEY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.images.ImageResponse;
import com.shakiv_husain.disneywatch.network.ApiClient;
import com.shakiv_husain.disneywatch.network.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieImagesRepository {


    public static final String TAG = MovieImagesRepository.class.getName();
    private ApiServices apiServices;


    public MovieImagesRepository() {
        this.apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<ImageResponse> getMovieImages(String movie_id) {

        MutableLiveData<ImageResponse> imageResponse = new MutableLiveData<>();


        apiServices.getMovieImages(movie_id, API_KEY).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    imageResponse.postValue(response.body());
                } else {
                    imageResponse.postValue(null);
                    Log.d(TAG, "onResponse: " + response.errorBody());
                }
                Log.d(TAG, "Images Api Call : " + call.request().url());

            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                imageResponse.postValue(null);
                Log.e(TAG, "onImagesResponse: " + t.getMessage());
            }
        });

        return imageResponse;

    }


}
