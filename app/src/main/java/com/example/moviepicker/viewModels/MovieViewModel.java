package com.example.moviepicker.viewModels;

import android.util.Log;

import com.example.moviepicker.models.MovieModel;
import com.example.moviepicker.repositories.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieViewModel() {

        movieRepository = MovieRepository.getInstance();

    }

    public LiveData<List<MovieModel>> getMovies(){

        return movieRepository.getMovies();

    }

    public LiveData<List<MovieModel>> getPopularMovies(){

        return movieRepository.getPopularMovies();

    }

    public LiveData<List<MovieModel>> getUpcomingMovies(){

        return movieRepository.getUpcomingMovies();

    }


    //calling search movie method from repository

    public void searchMovieApi(String query, int pageNumber){

        //Log.v("searchCall","from ViewModel query: "+query+" page: "+pageNumber);

        movieRepository.searchMovie(query,pageNumber);

    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }



    public void searchPopularMovieApi(int pageNumber){

        //Log.v("searchCall","from ViewModel query: "+query+" page: "+pageNumber);

        movieRepository.searchPopularMovie(pageNumber);

    }

    public void searchPopularNextPage(){
        movieRepository.searchPopularNextPage();
    }


    public void searchUpcomingMovieApi(int pageNumber){

        //Log.v("searchCall","from ViewModel query: "+query+" page: "+pageNumber);

        movieRepository.searchUpcomingMovie(pageNumber);

    }

    public void searchUpcomingNextPage(){
        movieRepository.searchUpcomingNextPage();
    }

}
