package com.shakiv_husain.disneywatch.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.shakiv_husain.disneywatch.R;
import com.shakiv_husain.disneywatch.databinding.ActivityWatchListBinding;
import com.shakiv_husain.disneywatch.viewmodel.WatchListViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel watchListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);

        initialization();

    }

    private void initialization() {


        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);

    }


    @Override
    protected void onResume() {
        super.onResume();

        loadWatchList();
    }

    private void loadWatchList() {
        activityWatchListBinding.setIsLoading(true);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(movieModels -> {
                    activityWatchListBinding.setIsLoading(false);

                    Toast.makeText(this, "Size Of Db " + movieModels.size(), Toast.LENGTH_SHORT).show();

                }));

    }
}