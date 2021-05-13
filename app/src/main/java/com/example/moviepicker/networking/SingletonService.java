package com.example.moviepicker.networking;

import com.example.moviepicker.utils.ApiCredentials;
import com.example.moviepicker.utils.TMDBApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingletonService {

    //Creating Singleton pattern with retrofit

    private  static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(ApiCredentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static TMDBApi tmdbApi = retrofit.create(TMDBApi.class);

    public static TMDBApi getTmdbApi(){
        return tmdbApi;
    }
}
