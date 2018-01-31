package com.example.ab0034.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                Api.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();

                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_row, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
            }
        });
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

        private List<Movie> movies;
        private Context context;

        public class MovieViewHolder extends RecyclerView.ViewHolder {
            LinearLayout moviesLayout;
            TextView movieTitle;
            TextView data;
            TextView movieDescription;
            TextView rating;

            public MovieViewHolder(View v) {
                super(v);
                moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
                movieTitle = (TextView) v.findViewById(R.id.title);
                data = (TextView) v.findViewById(R.id.subtitle);
                movieDescription = (TextView) v.findViewById(R.id.description);
                rating = (TextView) v.findViewById(R.id.rating);


            }
        }

        public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
            this.movies = movies;
            this.context = context;
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, final int position) {
            final String movidid = movies.get(position).getId().toString();
            holder.movieTitle.setText(movies.get(position).getTitle());
            holder.data.setText(movies.get(position).getReleaseDate());
            holder.movieDescription.setText(movies.get(position).getOverview());
            holder.rating.setText(movies.get(position).getVoteAverage().toString());
            holder.moviesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MovieDescription.class);
                    intent.putExtra("Id", movidid);
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }
}
