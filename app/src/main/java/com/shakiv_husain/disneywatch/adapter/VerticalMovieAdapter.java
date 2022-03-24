package com.shakiv_husain.disneywatch.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.VerticalItemContainerBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;

import java.util.List;

public class VerticalMovieAdapter extends RecyclerView.Adapter<VerticalMovieAdapter.VerticalMoviesViewHolder> {

    private List<MovieModel> movieModelList;
    private MovieListener listener;
    private LayoutInflater layoutInflater;


    public VerticalMovieAdapter(List<MovieModel> movieModelList, MovieListener listener) {
        this.movieModelList = movieModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VerticalMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        VerticalItemContainerBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.vertical_item_container,
                parent, false);

        return new VerticalMoviesViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull VerticalMoviesViewHolder holder, int position) {

        holder.bindTvShow(movieModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }


    public class VerticalMoviesViewHolder extends RecyclerView.ViewHolder {
        VerticalItemContainerBinding verticalItemContainerBinding;

        public VerticalMoviesViewHolder(@NonNull VerticalItemContainerBinding verticalItemContainerBinding) {
            super(verticalItemContainerBinding.getRoot());
            this.verticalItemContainerBinding = verticalItemContainerBinding;
        }

        private void bindTvShow(MovieModel movieModel) {
            verticalItemContainerBinding.setMovie(movieModel);
            verticalItemContainerBinding.executePendingBindings();
            verticalItemContainerBinding.getRoot().setOnClickListener(view -> listener.onTvShowClicked(movieModel));
        }
    }
}
