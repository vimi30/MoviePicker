package com.example.moviepicker.utils;

import com.example.moviepicker.models.MovieModel;
import com.example.moviepicker.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApi {

    //movie search
    @GET("/3/search/movie")
    Call<MovieSearchResponse>  searchMovie(

            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page

    );

    //get popular Movies
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String api_key,
            @Query("page") int page

    );


    //get Upcoming Movies
    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getUpcomingMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );


    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(

            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key

    );
}
