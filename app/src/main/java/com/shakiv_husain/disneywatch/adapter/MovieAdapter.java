package com.shakiv_husain.disneywatch.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.MovieItemMainBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private MovieListener movieListener;
    private List<MovieModel> modelList;
    private LayoutInflater inflater;


    public MovieAdapter(MovieListener movieListener, List<MovieModel> modelList) {
        this.movieListener = movieListener;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_item_main, parent, false);
        return new MovieAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        holder.bindTvShow(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {


        MovieItemMainBinding movieItemMainBinding;


        public MovieAdapterViewHolder(@NonNull MovieItemMainBinding movieItemMainBinding) {
            super(movieItemMainBinding.getRoot());
            this.movieItemMainBinding = movieItemMainBinding;
        }


        public void bindTvShow(MovieModel movieModel) {
            movieItemMainBinding.setMovie(movieModel);
            movieItemMainBinding.executePendingBindings();
            movieItemMainBinding.getRoot().setOnClickListener(view -> movieListener.onTvShowClicked(movieModel));
        }

    }

}
