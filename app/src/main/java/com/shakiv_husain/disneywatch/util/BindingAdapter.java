package com.shakiv_husain.disneywatch.util;

import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.IMAGE_BASE_URL;
import static com.shakiv_husain.disneywatch.util.constants.ApiConstants.IMAGE_SIZE;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class BindingAdapter {


    @androidx.databinding.BindingAdapter("android:imageURL")
    public static void setImageUrl(ImageView imageView, String url) {

        try {
            String imgUrl = IMAGE_BASE_URL + IMAGE_SIZE + url;
            Glide.with(imageView.getContext()).load(imgUrl)
                    .transform(new FitCenter(), new RoundedCorners(28))
                    .transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "setImageUrl: " + e.getMessage());
        }

    }
}
