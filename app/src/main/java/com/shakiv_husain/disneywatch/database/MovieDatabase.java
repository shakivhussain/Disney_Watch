package com.shakiv_husain.disneywatch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shakiv_husain.disneywatch.dao.MovieDao;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;

@Database(entities = MovieModel.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabase;


    public static synchronized MovieDatabase getMovieDatabase(Context context) {

        if (movieDatabase == null) {

            movieDatabase = Room.databaseBuilder(
                    context,
                    MovieDatabase.class,
                    "movie_database_db"
            ).build();
        }

        return movieDatabase;
    }

    public abstract MovieDao getMovieDao();

}
