package com.shakiv_husain.disneywatch.util;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.shakiv_husain.disneywatch.R;

public final class Util {


    public static void setUpSliderIndicator(Context context, LinearLayout sliderIndicator, int size) {

        ImageView[] indicators = new ImageView[size];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new ImageView(context);
            indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.background_slider_indicator_inactive)
            );

            indicators[i].setLayoutParams(layoutParams);
            sliderIndicator.addView(indicators[i]);
        }


    }

    public static void setCurrentSliderIndicator(Context context, LinearLayout sliderIndicator, int position) {

        int childCount = sliderIndicator.getChildCount();

        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) sliderIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.background_slider_indicator_inactive));
            }
        }
    }
}
