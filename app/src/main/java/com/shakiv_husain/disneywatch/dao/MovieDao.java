package com.shakiv_husain.disneywatch.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.shakiv_husain.disneywatch.models.movie.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM  movie_model")
    Flowable<List<MovieModel>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(MovieModel tvshow);

    @Delete
    Completable removeFromWatchList(MovieModel movieModel);


    @Query("SELECT * FROM movie_model WHERE id = :movieId ")
    Flowable<MovieModel> getMoviesFromWatchList(String movieId);

}
