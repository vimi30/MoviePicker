package com.example.moviepicker.response;

import com.example.moviepicker.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// this class is for getting single movie object

public class SingleMovieResponse {

    // finding the movie object by a serializeName
    @SerializedName("results") // this will serialize and deserialize the array titled as "results"
    @Expose //this will also do the same for Gson library

    private MovieModel movieModel;

    public MovieModel getMovieModel(){
        return movieModel;
    }


    @Override
    public String toString() {
        return "SingleMovieResponse{" +
                "movieModel=" + movieModel +
                '}';
    }
}
