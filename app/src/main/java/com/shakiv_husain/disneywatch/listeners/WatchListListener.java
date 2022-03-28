package com.shakiv_husain.disneywatch.listeners;

import com.shakiv_husain.disneywatch.models.movie.MovieModel;

public interface WatchListListener {


    void onMovieClicked(MovieModel movie);

    void removeMovieFromWatchlist(MovieModel movie, int position);

}
