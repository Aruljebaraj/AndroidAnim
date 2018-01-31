package com.example.ab0034.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AB0034 on 28-12-2017.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") String id, @Query("api_key") String apiKey);


    @GET("movie/vote_count")
    Call<MovieResponse> getVoteCount(@Query("api_key") String apiKey);
}
