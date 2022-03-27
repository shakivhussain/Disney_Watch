package com.shakiv_husain.disneywatch.network;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.API_KEY_;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.IMAGES;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.MOVIE_DETAIL;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.MOVIE_ID;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.PAGE;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.POPULAR_MOVIES;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.QUERY;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.SEARCH_COLLECTIONS;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.SEARCH_MOVIES;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.SEARCH_TV_SHOWS;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.SIMILAR;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.TOP_RATED_MOVIES;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.UPCOMING_MOVIES;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.VIDEOS;

import com.shakiv_husain.disneywatch.models.Video.VideoResponse;
import com.shakiv_husain.disneywatch.models.images.ImageResponse;
import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.models.movie.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {


    @GET(POPULAR_MOVIES)
    Call<MoviesResponse> getPopularMovies(
            @Query(PAGE) int page,
            @Query(API_KEY_) String api_key);


    @GET(UPCOMING_MOVIES)
    Call<MoviesResponse> getUpcomingMovies(
            @Query(PAGE) int page,
            @Query(API_KEY_) String api_key
    );


    @GET(TOP_RATED_MOVIES)
    Call<MoviesResponse> getTopRatedMovies(
            @Query(PAGE) int page,
            @Query(API_KEY_) String api_key
    );


    @GET(SEARCH_MOVIES)
    Call<MoviesResponse> searchMovies(
            @Query(PAGE) int page,
            @Query(QUERY) String query,
            @Query(API_KEY_) String api_key
    );

    @GET(SEARCH_COLLECTIONS)
    Call<MoviesResponse> searchCollections(
            @Query(PAGE) int page,
            @Query(QUERY) String query,
            @Query(API_KEY_) String api_key
    );


    @GET(SEARCH_TV_SHOWS)
    Call<MoviesResponse> searchTvShows(
            @Query(PAGE) int page,
            @Query(QUERY) String query,
            @Query(API_KEY_) String api_key
    );


    @GET(MOVIE_DETAIL)
    Call<MovieDetailsResponse> getMovieDetails(
            @Path(MOVIE_ID) String movie_id,
            @Query(API_KEY_) String api_key);


    @GET(IMAGES)
    Call<ImageResponse> getMovieImages(
            @Path(MOVIE_ID) String movie_id,
            @Query(API_KEY_) String api_key
    );


    @GET(VIDEOS)
    Call<VideoResponse> getVideos(
            @Path(MOVIE_ID) String movie_id,
            @Query(API_KEY_) String api_key
    );

    @GET(SIMILAR)
    Call<MoviesResponse> getSimilarMovies(
            @Path(MOVIE_ID) String movie_id,
            @Query(PAGE) int page,
            @Query(API_KEY_) String api_key
    );

}
