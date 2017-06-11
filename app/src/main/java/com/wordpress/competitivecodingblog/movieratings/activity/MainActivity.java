package com.wordpress.competitivecodingblog.movieratings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.wordpress.competitivecodingblog.movieratings.R;
import com.wordpress.competitivecodingblog.movieratings.adapter.MoviesAdapter;
import com.wordpress.competitivecodingblog.movieratings.model.Movie;
import com.wordpress.competitivecodingblog.movieratings.model.MoviesResponse;
import com.wordpress.competitivecodingblog.movieratings.rest.ApiClient;
import com.wordpress.competitivecodingblog.movieratings.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "56bb9b979fd1c1dbf8618c95ba665c66";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_movie_item, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}