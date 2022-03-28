package com.shakiv_husain.disneywatch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.MovieItemMainBinding;
import com.shakiv_husain.disneywatch.listeners.WatchListListener;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListAdapterViewHolder> {
    private WatchListListener movieListener;
    private List<MovieModel> modelList;
    private LayoutInflater inflater;


    public WatchListAdapter(WatchListListener movieListener, List<MovieModel> modelList) {
        this.movieListener = movieListener;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public WatchListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_item_main, parent, false);
        return new WatchListAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListAdapterViewHolder holder, int position) {
        holder.bindTvShow(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class WatchListAdapterViewHolder extends RecyclerView.ViewHolder {


        MovieItemMainBinding movieItemMainBinding;


        public WatchListAdapterViewHolder(@NonNull MovieItemMainBinding movieItemMainBinding) {
            super(movieItemMainBinding.getRoot());
            this.movieItemMainBinding = movieItemMainBinding;
        }


        public void bindTvShow(MovieModel movieModel) {
            movieItemMainBinding.setMovie(movieModel);
            movieItemMainBinding.executePendingBindings();
            movieItemMainBinding.getRoot().setOnClickListener(view -> movieListener.onMovieClicked(movieModel));
            movieItemMainBinding.imageDelte.setOnClickListener(view -> movieListener.removeMovieFromWatchlist(movieModel, getAdapterPosition()));
            movieItemMainBinding.imageDelte.setVisibility(View.VISIBLE);
        }
    }
}
