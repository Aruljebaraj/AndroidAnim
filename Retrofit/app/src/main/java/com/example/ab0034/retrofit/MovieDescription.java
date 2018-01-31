package com.example.ab0034.retrofit;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDescription extends AppCompatActivity {

    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";

    String movieId = "";
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView TxtMovieDescription, TxtCastandCrewDetails,
            TxtMovieReleasedDate, TxtLanguage, txtMoviename, TxtMoviePopularity, TxtMovieRating;
    ImageView Poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        if (getIntent().getExtras() != null) {
            movieId = getIntent().getExtras().getString("Id");
        }
        Poster = (ImageView) findViewById(R.id.Poster);
        TxtMovieDescription = (TextView) findViewById(R.id.TxtMovieDescription);

        TxtCastandCrewDetails = (TextView) findViewById(R.id.TxtCastandCrewDetails);
        TxtMoviePopularity = (TextView) findViewById(R.id.TxtMoviePopularity);
        TxtMovieRating = (TextView) findViewById(R.id.TxtMovieRating);

        TxtMovieReleasedDate = (TextView) findViewById(R.id.TxtMovieReleasedDate);
        TxtLanguage = (TextView) findViewById(R.id.TxtOriginalLanguage);
        txtMoviename = (TextView) findViewById(R.id.txtMoviename);
        ApiInterface apiService =
                Api.getClient().create(ApiInterface.class);

        Call<Movie> call = apiService.getMovieDetails(movieId, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
//                MovieResponse movies = response.body();
//                List<Movie> mov  = response.body().getResults();
//                int position = mov.size();
                String title = response.body().getTitle();
                String release = response.body().getReleaseDate();
                String lang = response.body().getOriginalLanguage();
                String overview = response.body().getOverview();
                String rating = response.body().getVoteAverage().toString();
                String pop = response.body().getPopularity().toString();
                TxtMovieReleasedDate.setText(release);
                txtMoviename.setText(title);
//                url=(movies.get(position).getPosterPath());
//                Glide.with(MovieDescription.this).load(url).into(Poster);

                TxtLanguage.setText(lang);
                TxtMovieDescription.setText(overview);
                TxtMovieRating.setText(rating);
                TxtMoviePopularity.setText(pop);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }

        });
    }
}
