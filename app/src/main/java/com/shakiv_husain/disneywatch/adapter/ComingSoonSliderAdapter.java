package com.shakiv_husain.disneywatch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ItemContainerSliderBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;

import java.util.List;

public class ComingSoonSliderAdapter extends RecyclerView.Adapter<ComingSoonSliderAdapter.SliderAdapterViewHolder> {
    private MovieListener movieListener;
    private List<MovieModel> moviesList;
    private LayoutInflater inflater;
    private ViewPager2 viewPager2;


    public ComingSoonSliderAdapter(MovieListener listener, List<MovieModel> movies, ViewPager2 comingSoonViewPager) {
        this.movieListener = listener;
        this.moviesList = movies;
        this.viewPager2 = comingSoonViewPager;

    }

    @NonNull
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_container_slider, parent, false);
        return new SliderAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapterViewHolder holder, int position) {
        holder.bindTvShow(moviesList.get(position));

        if (position == moviesList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    class SliderAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemContainerSliderBinding itemContainerSliderBinding;

        public SliderAdapterViewHolder(@NonNull ItemContainerSliderBinding itemContainerSliderBinding) {
            super(itemContainerSliderBinding.getRoot());
            this.itemContainerSliderBinding = itemContainerSliderBinding;
        }

        public void bindTvShow(MovieModel movieModel) {
            itemContainerSliderBinding.setImageUrl(movieModel.getBackdropPath());
            itemContainerSliderBinding.setDateEnable(true);
            itemContainerSliderBinding.setNameEnable(true);
            itemContainerSliderBinding.setName(movieModel.getTitle());
            itemContainerSliderBinding.setReleaseDate(movieModel.getReleaseDate());
            itemContainerSliderBinding.executePendingBindings();
            itemContainerSliderBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieListener.onTvShowClicked(movieModel);
                }
            });
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            moviesList.addAll(moviesList);
            notifyDataSetChanged();
        }
    };


}
