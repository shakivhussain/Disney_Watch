
package com.shakiv_husain.disneywatch.models.popular_movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesResponse {

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("results")
    @Expose
    public List<MovieModel> movieModels = null;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("total_results")
    @Expose
    public int totalResults;


    public int getPage() {
        return page;
    }

    public List<MovieModel> getMovies() {
        return movieModels;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }


    @Override
    public String toString() {
        return "MoviesResponse{" +
                "page=" + page +
                ", results=" + movieModels +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }
}


