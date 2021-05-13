package com.example.moviepicker.networking;

import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.moviepicker.AppExecutors;
import com.example.moviepicker.models.MovieModel;
import com.example.moviepicker.response.MovieSearchResponse;
import com.example.moviepicker.utils.ApiCredentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //Live data for Search
    private MutableLiveData<List<MovieModel>> movies;
    private static MovieApiClient movieApiClientInstance;

    //making global Runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //Live data for popular movies
    private MutableLiveData<List<MovieModel>> popularMovies;
    // runnable for fetching the popular movies
    private RetrievePopularMoviesRunnable retrievePopularMoviesRunnable;


    //Live data for Upcoming movies
    private MutableLiveData<List<MovieModel>> upcomingMovies;
    // runnable for fetching the Upcoming movies
    private RetrieveUpcomingMoviesRunnable retrieveUpcomingMoviesRunnable;


    public static MovieApiClient getInstance(){

        if(movieApiClientInstance==null){
            movieApiClientInstance = new MovieApiClient();
        }

        return movieApiClientInstance;

    }

    private MovieApiClient(){

        movies = new MutableLiveData<>();
        popularMovies = new MutableLiveData<>();
        upcomingMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movies;
    }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return popularMovies;
    }
    public LiveData<List<MovieModel>> getUpcomingMovies(){
        return upcomingMovies;
    }




    //This method will be called from other classes
    public void searchMovieApi(String query,int pageNumber){

        if(retrieveMoviesRunnable!=null){
            retrieveMoviesRunnable = null;
        }

        Log.v("searchCall","from MovieClient query: "+query+" page: "+pageNumber);

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //canceling the retrofit request

                myHandler.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);// if in 4 seconds we don't have any response, cancel the call

    }

    //popular movie fetcher
    public void searchPopularMovieApi(int pageNumber){

        if(retrievePopularMoviesRunnable!=null){
            retrievePopularMoviesRunnable = null;
        }

        Log.v("searchCall"," page: "+pageNumber);

        retrievePopularMoviesRunnable = new RetrievePopularMoviesRunnable(pageNumber);

        final Future myPopHandler = AppExecutors.getInstance().networkIO().submit(retrievePopularMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //canceling the retrofit request

                myPopHandler.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);// if in 4 seconds we don't have any response, cancel the call

    }

    //Upcoming Movie fetcher

    public void searchUpcomingMovieApi(int pageNumber){

        if(retrieveUpcomingMoviesRunnable!=null){
            retrieveUpcomingMoviesRunnable = null;
        }

        Log.v("UpComingSearchCall"," page: "+pageNumber);

        retrieveUpcomingMoviesRunnable = new RetrieveUpcomingMoviesRunnable(pageNumber);

        final Future myUpcomingHandler = AppExecutors.getInstance().networkIO().submit(retrieveUpcomingMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //canceling the retrofit request

                myUpcomingHandler.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);// if in 4 seconds we don't have any response, cancel the call

    }





//Retrieving data form RESTApi by runnable class
// We have 2 types of query


    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {

            //Log.v("searchCall","from runnable initialized. query: "+query+" page: "+pageNumber);
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }


        @Override
        public void run() {

            try {
                Response response = getMovies(query,pageNumber).execute();
                if(cancelRequest){return;}

                if(response.code()==200){

                    Log.v("searchCall","from response Found! query: "+query+" page: "+pageNumber);

                    List<MovieModel> movieList = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    for(MovieModel movie:movieList){
                        Log.v("searchCall","Movie: "+movie.getTitle()+" backdrop: "+movie.getBackdrop_path());
                    }

                    //movies.postValue(movieList);


                    if(pageNumber ==1){
                        //sending data to live data
                        //postValue: used for background thread
                        //setValue: not for background thread
                        movies.postValue(movieList);
                        Log.v("searchCall","from inside run() OnePage query: "+query+" page: "+pageNumber);
                    }else{

                        List<MovieModel> currentMovies = movies.getValue();
                        currentMovies.addAll(movieList);
                        movies.postValue(currentMovies);
                        Log.v("searchCall","from inside run() More Than One query: "+query+" page: "+pageNumber);

                    }


                }else {

                    String error = response.errorBody().string();
                    Log.v("TAG","Error: "+error);
                    movies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                movies.postValue(null);
            }


        }

            //Search Method/Query

//            Call<MovieSearchResponse> responseCall = tmdbApi.searchMovie(ApiCredentials.API_KEY,"Love and Monsters","1");
        private Call<MovieSearchResponse> getMovies(String query,int pageNumber){
            return SingletonService.getTmdbApi().searchMovie(
                    ApiCredentials.API_KEY,
                    query,
                    pageNumber);

        }

        private void cancelRequest(){
            Log.v("TAG","Cancelling the Search Request");
            cancelRequest = true;
        }

        }

    private class RetrievePopularMoviesRunnable implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrievePopularMoviesRunnable(int pageNumber) {

            //Log.v("searchCall","from runnable initialized. query: "+query+" page: "+pageNumber);
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }


        @Override
        public void run() {

            try {
                Response response = getPopularMovies(pageNumber).execute();
                if(cancelRequest){return;}

                if(response.code()==200){

                    Log.v("searchCall"," page: "+pageNumber);

                    List<MovieModel> movieList = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    for(MovieModel movie:movieList){
                        Log.v("searchCall","Movie: "+movie.getTitle()+" backdrop: "+movie.getBackdrop_path());
                    }

                    //movies.postValue(movieList);


                    if(pageNumber ==1){
                        //sending data to live data
                        //postValue: used for background thread
                        //setValue: not for background thread
                        popularMovies.postValue(movieList);
                        Log.v("searchCall"," page: "+pageNumber);
                    }else{

                        List<MovieModel> currentMovies = popularMovies.getValue();
                        currentMovies.addAll(movieList);
                        popularMovies.postValue(currentMovies);
                        Log.v("searchCall"," page: "+pageNumber);

                    }


                }else {

                    String error = response.errorBody().string();
                    Log.v("TAG","Error: "+error);
                    popularMovies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                popularMovies.postValue(null);
            }


        }

        //Search Method/Query

        //            Call<MovieSearchResponse> responseCall = tmdbApi.searchMovie(ApiCredentials.API_KEY,"Love and Monsters","1");
        private Call<MovieSearchResponse> getPopularMovies(int pageNumber){
            return SingletonService.getTmdbApi().getPopularMovies(
                    ApiCredentials.API_KEY,
                    pageNumber);

        }

        private void cancelRequest(){
            Log.v("TAG","Cancelling the Search Request");
            cancelRequest = true;
        }

    }

    private class RetrieveUpcomingMoviesRunnable implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveUpcomingMoviesRunnable(int pageNumber) {

            //Log.v("searchCall","from runnable initialized. query: "+query+" page: "+pageNumber);
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }


        @Override
        public void run() {

            try {
                Response response = getUpcomingMovies(pageNumber).execute();
                if(cancelRequest){return;}

                if(response.code()==200){

                    Log.v("UpcomingSearchCall"," page: "+pageNumber);

                    List<MovieModel> movieList = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    for(MovieModel movie:movieList){
                        Log.v("UpcomingSearchCall","Movie: "+movie.getTitle()+" ReleaseDate: "+movie.getRelease_date());
                    }

                    //movies.postValue(movieList);


                    if(pageNumber ==1){
                        //sending data to live data
                        //postValue: used for background thread
                        //setValue: not for background thread
                        upcomingMovies.postValue(movieList);
                    }else{

                        List<MovieModel> currentMovies = upcomingMovies.getValue();
                        currentMovies.addAll(movieList);
                        upcomingMovies.postValue(currentMovies);

                    }
                    Log.v("searchCall"," page: "+pageNumber);


                }else {

                    String error = response.errorBody().string();
                    Log.v("TAG","Error: "+error);
                    upcomingMovies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                upcomingMovies.postValue(null);
            }


        }

        //Search Method/Query

        //            Call<MovieSearchResponse> responseCall = tmdbApi.searchMovie(ApiCredentials.API_KEY,"Love and Monsters","1");
        private Call<MovieSearchResponse> getUpcomingMovies(int pageNumber){
            return SingletonService.getTmdbApi().getUpcomingMovies(
                    ApiCredentials.API_KEY,
                    pageNumber);

        }

        private void cancelRequest(){
            Log.v("TAG","Cancelling the Search Request");
            cancelRequest = true;
        }

    }




}



