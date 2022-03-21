package com.shakiv_husain.disneywatch.activities;

import static com.shakiv_husain.disneywatch.util.constants.AppConstants.ID;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.SliderAdapter;
import com.shakiv_husain.disneywatch.adapter.VerticalMovieAdapter;
import com.shakiv_husain.disneywatch.adapter.YoutubeVideosAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivityMovieDetailsBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.Video.VideoModel;
import com.shakiv_husain.disneywatch.models.Video.VideoResponse;
import com.shakiv_husain.disneywatch.models.images.Backdrop;
import com.shakiv_husain.disneywatch.models.images.ImageResponse;
import com.shakiv_husain.disneywatch.models.movie_details.MovieDetailsResponse;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;
import com.shakiv_husain.disneywatch.models.popular_movie.MoviesResponse;
import com.shakiv_husain.disneywatch.util.Util;
import com.shakiv_husain.disneywatch.viewmodel.MovieDetailsViewModel;
import com.shakiv_husain.disneywatch.viewmodel.MovieImagesViewModel;
import com.shakiv_husain.disneywatch.viewmodel.MovieVideosViewModel;
import com.shakiv_husain.disneywatch.viewmodel.MoviesViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MovieDetailsActivity extends AppCompatActivity implements MovieListener {

    private static final String TAG = MovieDetailsActivity.class.getName();
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieImagesViewModel movieImagesViewModel;
    private MovieVideosViewModel movieVideosViewModel;
    private MoviesViewModel moviesViewModel;
    private ActivityMovieDetailsBinding movieDetailsBinding;

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
        movieVideosViewModel = new ViewModelProvider(this).get(MovieVideosViewModel.class);
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);


        // Time Initialize
        timer = new Timer(); // This will create a new Thread

        movieDetailsBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });

        getMovieDetails(id);
        setMovieImages(id);
        setVideos(id);
        getSimilarMovies(id);

    }

    private void getSimilarMovies(String id) {


        moviesViewModel.getSimilarMovies(id, 1).observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                Log.d(TAG, "getSimilarMovies: " + moviesResponse.getMovies().size());


                setSimilarMoviesAdapter(moviesResponse.getMovies());
            }
        });

    }

    private void setSimilarMoviesAdapter(List<MovieModel> movies) {

        if (movies.size() > 0) {
            movieDetailsBinding.tvSimilarMovies.setVisibility(View.VISIBLE);
            movieDetailsBinding.similarMoviesVP.setVisibility(View.VISIBLE);


            movieDetailsBinding.similarMoviesVP.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            movieDetailsBinding.similarMoviesVP.setAdapter(new VerticalMovieAdapter(movies, this));
        }
    }

    private void setVideos(String movie_id) {


        movieVideosViewModel.getVideos(movie_id).observe(this, new Observer<VideoResponse>() {
            @Override
            public void onChanged(VideoResponse videoResponse) {
                setVideoAdapter(videoResponse.getResults());
            }
        });


        movieDetailsBinding.videosViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Util.setCurrentSliderIndicator(getApplicationContext(), movieDetailsBinding.videosIndicator, position);
            }
        });
        Util.setCurrentSliderIndicator(getApplicationContext(), movieDetailsBinding.videosIndicator, 0);
    }

    private void setVideoAdapter(List<VideoModel> videoModelList) {

        if (videoModelList.size() > 0) {

            movieDetailsBinding.tvVideosTrailer.setVisibility(View.VISIBLE);
            movieDetailsBinding.videosViewPager.setVisibility(View.VISIBLE);
            movieDetailsBinding.videosIndicator.setVisibility(View.VISIBLE);

//                String[] videoIds = {"6JYIGclVQdw", "LvetJ9U_tVY", "6JYIGclVQdw", "LvetJ9U_tVY", "6JYIGclVQdw", "LvetJ9U_tVY", "6JYIGclVQdw", "LvetJ9U_tVY", "6JYIGclVQdw", "LvetJ9U_tVY", "6JYIGclVQdw", "LvetJ9U_tVY"};
            movieDetailsBinding.videosViewPager.setOffscreenPageLimit(1);
            movieDetailsBinding.videosViewPager.setAdapter(new YoutubeVideosAdapter(videoModelList, getLifecycle()));
            Util.setUpSliderIndicator(getApplicationContext(), movieDetailsBinding.videosIndicator, videoModelList.size());
        }

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

        movieDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        movieDetailsBinding.sliderIndicator.setVisibility(View.VISIBLE);
        movieDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        movieDetailsBinding.sliderViewPager.setAdapter(new SliderAdapter(imageList));

        if (imageList.size() > 7) {
            Util.setUpSliderIndicator(getApplicationContext(), movieDetailsBinding.sliderIndicator, imageList.size() / 2);
        } else {
            Util.setUpSliderIndicator(getApplicationContext(), movieDetailsBinding.sliderIndicator, imageList.size());
        }

        movieDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Util.setCurrentSliderIndicator(getApplicationContext(), movieDetailsBinding.sliderIndicator, position);
            }
        });

        Util.setCurrentSliderIndicator(getApplicationContext(), movieDetailsBinding.sliderIndicator, 0);

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

        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void getMovieDetails(String id) {


        movieDetailsViewModel.getMovieDetails(id).observe(this, new Observer<MovieDetailsResponse>() {
            @Override
            public void onChanged(MovieDetailsResponse movieDetailsResponse) {

                if (movieDetailsResponse != null) {
                    movieDetailsBinding.progressBar.setVisibility(View.GONE);
                    setData(movieDetailsResponse);
                }

            }
        });
    }

    private void setData(MovieDetailsResponse movieDetailsResponse) {


        movieDetailsBinding.ivPoster.setVisibility(View.VISIBLE);
        movieDetailsBinding.tvTitle.setVisibility(View.VISIBLE);
        movieDetailsBinding.popularity.setVisibility(View.VISIBLE);
        movieDetailsBinding.tvStatus.setVisibility(View.VISIBLE);
        movieDetailsBinding.tvReleaseDate.setVisibility(View.VISIBLE);
        movieDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
        movieDetailsBinding.divider.setVisibility(View.VISIBLE);
        movieDetailsBinding.divider2.setVisibility(View.VISIBLE);
        movieDetailsBinding.tvDescription.setVisibility(View.VISIBLE);
        movieDetailsBinding.tvReadMore.setVisibility(View.VISIBLE);


        if (movieDetailsResponse.getPosterPath() != null)
            movieDetailsBinding.setImageUrl(movieDetailsResponse.getPosterPath());
        if (movieDetailsResponse.getTitle() != null)
            movieDetailsBinding.setTitle(movieDetailsResponse.getTitle());
        if (movieDetailsResponse.getStatus() != null)
            movieDetailsBinding.setStatus(movieDetailsResponse.getStatus());
        if (movieDetailsResponse.getReleaseDate() != null)
            movieDetailsBinding.setReleaseDate(movieDetailsResponse.getReleaseDate());
        if (!String.valueOf(movieDetailsResponse.getVoteCount()).isEmpty())
            movieDetailsBinding.setRating(String.valueOf(movieDetailsResponse.getVoteCount()));
        if (movieDetailsResponse.getGenres() != null)
            movieDetailsBinding.setGeneric(movieDetailsResponse.getGenres().get(0).name);
        if (!String.valueOf(movieDetailsResponse.getRuntime()).isEmpty())
            movieDetailsBinding.setRuntime(String.valueOf((int) movieDetailsResponse.getRuntime()));
        if (!String.valueOf(movieDetailsResponse.getPopularity()).isEmpty())
            movieDetailsBinding.setPopularity(String.valueOf((int) movieDetailsResponse.getPopularity()));
        if (movieDetailsResponse.getOverview() != null) {
            movieDetailsBinding.setDesc(
                    String.valueOf(
                            HtmlCompat.fromHtml(
                                    movieDetailsResponse.getOverview(), HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                    )
            );

        }

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

    @Override
    public void onTvShowClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(ID, movieModel.getId());
        startActivity(intent);
    }
}