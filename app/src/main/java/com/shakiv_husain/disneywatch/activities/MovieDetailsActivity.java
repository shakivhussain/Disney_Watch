package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.ID;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.SliderAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivityMovieDetailsBinding;
import com.shakiv_husain.disneywatch.models.images.Backdrop;
import com.shakiv_husain.disneywatch.models.images.ImageResponse;
import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.viewmodel.MovieDetailsViewModel;
import com.shakiv_husain.disneywatch.viewmodel.MovieImagesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MovieDetailsActivity extends AppCompatActivity {


    private static final String TAG = MovieDetailsActivity.class.getName();
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieImagesViewModel movieImagesViewModel;
    private ActivityMovieDetailsBinding movieDetailsBinding;
    private SliderAdapter sliderAdapter;
    private List<String> imageList;

    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 1500; // time in milliseconds between successive task executions.
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);


        initialization();


    }

    private void initialization() {
        String id = "";
        if (getIntent().hasExtra(ID)) {
            id = getIntent().getStringExtra(ID);
            Log.d(TAG, "initialization: " + id);
        }

        Log.d(TAG, "initialization: ");

        // View Model
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        movieImagesViewModel = new ViewModelProvider(this).get(MovieImagesViewModel.class);

        imageList = new ArrayList<>();

        movieDetailsBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });

        getMovieDetails(id);
        setMovieImages(id);

    }

    private void setMovieImages(String id) {

        movieImagesViewModel.getMovieImages(id).observe(this, new Observer<ImageResponse>() {
            @Override
            public void onChanged(ImageResponse imageResponse) {

                if (imageResponse != null) {

                    if (imageResponse != null) {

                        setAdapter(imageResponse.getBackdrops());
                    }
                }

            }

        });

    }

    private void setAdapter(List<Backdrop> imageList) {

        movieDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        movieDetailsBinding.sliderViewPager.setAdapter(new SliderAdapter(imageList));


        setUpSliderIndicator(imageList.size() / 2);

        movieDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                setCurrentSliderIndicator(position);

            }
        });

        setCurrentSliderIndicator(0);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == (imageList.size() / 2) - 1) {
                    currentPage = 0;
                }
                movieDetailsBinding.sliderViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void setCurrentSliderIndicator(int position) {

        int childCount = movieDetailsBinding.sliderIndicator.getChildCount();


        currentPage = position;
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) movieDetailsBinding.sliderIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    private void setUpSliderIndicator(int size) {


        ImageView[] indicators = new ImageView[size];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );


        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)
            );

            indicators[i].setLayoutParams(layoutParams);
            movieDetailsBinding.sliderIndicator.addView(indicators[i]);
        }


    }

    private void getMovieDetails(String id) {


        movieDetailsViewModel.getMovieDetails(id).observe(this, new Observer<MovieDetailsResponse>() {
            @Override
            public void onChanged(MovieDetailsResponse movieDetailsResponse) {

                setData(movieDetailsResponse);


            }
        });
    }

    private void setData(MovieDetailsResponse movieDetailsResponse) {

        movieDetailsBinding.setImageUrl(movieDetailsResponse.getPosterPath());
        movieDetailsBinding.setTitle(movieDetailsResponse.getTitle());
        movieDetailsBinding.setStatus(movieDetailsResponse.getStatus());
        movieDetailsBinding.setReleaseDate(movieDetailsResponse.getReleaseDate());
        movieDetailsBinding.setRating(String.valueOf(movieDetailsResponse.getVoteCount()));
        movieDetailsBinding.setGeneric(movieDetailsResponse.getGenres().get(0).name);
        movieDetailsBinding.setRuntime(String.valueOf((int) movieDetailsResponse.getRuntime()));
        movieDetailsBinding.setPopularity(String.valueOf((int) movieDetailsResponse.getPopularity()));
        movieDetailsBinding.setDesc(
                String.valueOf(
                        HtmlCompat.fromHtml(
                                movieDetailsResponse.getOverview(), HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                )
        );

        movieDetailsBinding.tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (movieDetailsBinding.tvReadMore.getText().toString().equals("Read More")) {
                    movieDetailsBinding.tvDescription.setMaxLines(Integer.MAX_VALUE);
                    movieDetailsBinding.tvDescription.setEllipsize(null);
                    movieDetailsBinding.tvReadMore.setText("Read Less");
                } else {
                    movieDetailsBinding.tvDescription.setMaxLines(4);
                    movieDetailsBinding.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
                    movieDetailsBinding.tvReadMore.setText("Read More");
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}