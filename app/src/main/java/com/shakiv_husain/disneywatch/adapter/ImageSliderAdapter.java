package com.shakiv_husain.disneywatch.adapter;

import android.animation.Animator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ItemContainerSliderBinding;
import com.shakiv_husain.disneywatch.models.images.Backdrop;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderAdapterViewHolder> {

    private List<Backdrop> backdrops;
    private LayoutInflater inflater;


    public ImageSliderAdapter(List<Backdrop> imageUrls) {
        this.backdrops = imageUrls;
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
        holder.bindTvShow(backdrops.get(position).getFilePath());
    }

    @Override
    public int getItemCount() {
        if (backdrops.size() > 7) {
            return backdrops.size() / 2;
        } else {
            return backdrops.size();
        }
    }


    class SliderAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemContainerSliderBinding itemContainerSliderBinding;

        public SliderAdapterViewHolder(@NonNull ItemContainerSliderBinding itemContainerSliderBinding) {
            super(itemContainerSliderBinding.getRoot());
            this.itemContainerSliderBinding = itemContainerSliderBinding;


            itemContainerSliderBinding.lottieAnim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    itemContainerSliderBinding.lottieAnim.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    itemContainerSliderBinding.lottieAnim.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });


        }

        public void bindTvShow(String imUrl) {
            itemContainerSliderBinding.setImageUrl(imUrl);
        }
    }
}
