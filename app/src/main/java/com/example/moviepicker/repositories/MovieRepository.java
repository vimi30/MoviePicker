package com.example.moviepicker.repositories;

import android.util.Log;

import com.example.moviepicker.models.MovieModel;
import com.example.moviepicker.networking.MovieApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepository {

    private static MovieRepository movieRepositoryInstance;

    private MovieApiClient movieApiClient;

    private String movieQuery;
    private int pageNum;

    public static MovieRepository getInstance(){

        if(movieRepositoryInstance == null){
            movieRepositoryInstance = new MovieRepository();
        }

        return movieRepositoryInstance;
    }



    private MovieRepository(){
         movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieApiClient.getPopularMovies();
    }

    public LiveData<List<MovieModel>> getUpcomingMovies(){
        return movieApiClient.getUpcomingMovies();
    }




    //calling the search movie method from apiClient to repository
    public void searchMovie(String query, int pageNumber){

        movieQuery = query;
        pageNum = pageNumber;

       // Log.v("searchCall","from Repo query: "+query+" page: "+pageNumber);
        movieApiClient.searchMovieApi(query,pageNumber);

    }

    public void searchNextPage(){

        pageNum = pageNum+1;

        movieApiClient.searchMovieApi(movieQuery,pageNum);

    }


    //calling searchPopularMovie from apiClient
    public void searchPopularMovie(int pageNumber){

        pageNum = pageNumber;

        // Log.v("searchCall","from Repo query: "+query+" page: "+pageNumber);
        movieApiClient.searchPopularMovieApi(pageNum);

    }

    public void searchPopularNextPage(){

        pageNum = pageNum+1;
        movieApiClient.searchPopularMovieApi(pageNum);

    }

    //calling searchUpcomingMovies form apiClient

    public void searchUpcomingMovie(int pageNumber){
        pageNum = pageNumber;

        Log.v("UpComingSearchCall","from Repo;  page: "+pageNumber);
        movieApiClient.searchUpcomingMovieApi(pageNum);

    }

    public void searchUpcomingNextPage(){

        pageNum = pageNum+1;
        movieApiClient.searchUpcomingMovieApi(pageNum);

    }

}
