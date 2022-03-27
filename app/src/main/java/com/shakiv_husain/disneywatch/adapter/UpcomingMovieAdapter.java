package com.shakiv_husain.disneywatch.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ItemContainerSliderBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.movie.MovieModel;

import java.util.List;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieAdapterViewHolder> {

    private MovieListener movieListener;
    private List<MovieModel> modelList;
    private LayoutInflater inflater;
    private ViewPager2 viewPager2;


    public UpcomingMovieAdapter(MovieListener movieListener, List<MovieModel> modelList, ViewPager2 viewPager2) {
        this.movieListener = movieListener;
        this.modelList = modelList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public UpcomingMovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_container_slider, parent, false);
        return new UpcomingMovieAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMovieAdapterViewHolder holder, int position) {
        holder.bindTvShow(modelList.get(position));

        if (position == modelList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    class UpcomingMovieAdapterViewHolder extends RecyclerView.ViewHolder {


        ItemContainerSliderBinding movieItemMainBinding;


        public UpcomingMovieAdapterViewHolder(@NonNull ItemContainerSliderBinding itemContainerSliderBinding) {
            super(itemContainerSliderBinding.getRoot());
            this.movieItemMainBinding = itemContainerSliderBinding;
        }

        public void bindTvShow(MovieModel movieModel) {
            movieItemMainBinding.setImageUrl(movieModel.getPosterPath());
            movieItemMainBinding.executePendingBindings();
            movieItemMainBinding.getRoot().setOnClickListener(view -> movieListener.onTvShowClicked(movieModel));
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            modelList.addAll(modelList);
            notifyDataSetChanged();
        }
    };
}
