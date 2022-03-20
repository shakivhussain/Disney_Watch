package com.shakiv_husain.disneywatch.repositories;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.API_KEY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shakiv_husain.disneywatch.models.Video.VideoResponse;
import com.shakiv_husain.disneywatch.network.ApiClient;
import com.shakiv_husain.disneywatch.network.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieVideosRepository {

    private ApiServices apiServices;
    private static final String TAG = MovieVideosRepository.class.getName();

    public MovieVideosRepository() {
        this.apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<VideoResponse> getVideos(String movie_id) {

        MutableLiveData<VideoResponse> videos = new MutableLiveData<>();

        apiServices.getVideos(movie_id, API_KEY).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {

                if (response.isSuccessful()) {

                    videos.postValue(response.body());

                } else {
                    Log.d(TAG, "onResponse: " + response.errorBody());
                    videos.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return videos;
    }


}
