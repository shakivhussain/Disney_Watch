package com.shakiv_husain.disneywatch.network;

import static com.shakiv_husain.disneywatch.constants.ApiConstants.API_KEY;
import static com.shakiv_husain.disneywatch.constants.ApiConstants.API_KEY_;
import static com.shakiv_husain.disneywatch.constants.ApiConstants.PAGE;
import static com.shakiv_husain.disneywatch.constants.ApiConstants.POPULAR_MOVIES;

import com.shakiv_husain.disneywatch.models.popular.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {


    @GET(POPULAR_MOVIES)
    Call<MoviesResponse> getPopularMovies(@Query(PAGE) int page,
                                          @Query(API_KEY_) String api_key);



}
