package com.shakiv_husain.disneywatch.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.adapter.VerticalMovieAdapter;
import com.shakiv_husain.disneywatch.databinding.ActivitySearcBinding;
import com.shakiv_husain.disneywatch.listeners.MovieListener;
import com.shakiv_husain.disneywatch.models.popular_movie.MovieModel;
import com.shakiv_husain.disneywatch.models.popular_movie.MoviesResponse;
import com.shakiv_husain.disneywatch.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearcActivity extends AppCompatActivity implements MovieListener {

    private ActivitySearcBinding searcBinding;
    private MoviesViewModel moviesViewModel;
    private int currentPage = 1;
    private List<MovieModel> movieModelList = new ArrayList<>();
    private VerticalMovieAdapter movieAdaper;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searcBinding = DataBindingUtil.setContentView(this, R.layout.activity_searc);


        initialize();


    }

    private void initialize() {

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        movieAdaper = new VerticalMovieAdapter(movieModelList, this);
        searcBinding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        searcBinding.moviesRecyclerView.setAdapter(movieAdaper);

        searcBinding.inputSearch.requestFocus();
        searcBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                setSearchMovies(editable.toString());
                            });
                        }
                    }, 400);
                }

            }
        });

        searcBinding.moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searcBinding.moviesRecyclerView.canScrollVertically(1)) {
                    currentPage += 1;
                    setSearchMovies(searcBinding.inputSearch.getText().toString());
                }
            }
        });

    }

    private void setSearchMovies(String query) {

        moviesViewModel.searchMovies(currentPage, query).observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if (moviesResponse != null) {
                    if (moviesResponse.getMovies() != null) {
                        int count = movieModelList.size();
                        movieModelList.addAll(moviesResponse.getMovies());
                        movieAdaper.notifyItemRangeChanged(count, movieModelList.size());
                    }
                }
            }
        });
    }

    private void setData(List<MovieModel> movies) {

    }

    @Override
    public void onTvShowClicked(MovieModel movieModel) {
        Toast.makeText(this, "Name : " + movieModel.getTitle(), Toast.LENGTH_SHORT).show();
    }
}