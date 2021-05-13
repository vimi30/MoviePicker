package com.example.moviepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviepicker.adaters.MovieRecyclerViewAdapter;
import com.example.moviepicker.adaters.OnMovieClickListener;
import com.example.moviepicker.adaters.PopularMovieRVAdapter;
import com.example.moviepicker.adaters.UpcomingMovieRVAdapter;
import com.example.moviepicker.models.MovieModel;
import com.example.moviepicker.networking.SingletonService;
import com.example.moviepicker.response.MovieSearchResponse;
import com.example.moviepicker.response.SingleMovieResponse;
import com.example.moviepicker.utils.ApiCredentials;
import com.example.moviepicker.utils.TMDBApi;
import com.example.moviepicker.viewModels.MovieViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    //Network Security Configuration

    RecyclerView recyclerView, popularMovieRV,upComingMovieRV;


    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private PopularMovieRVAdapter popularMovieRVAdapter;
    private UpcomingMovieRVAdapter upcomingMovieRVAdapter;

    Button button;
    //ViewModel
    private MovieViewModel movieViewModel;
    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolBar
        Toolbar toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        setUpSearchView();

        recyclerView = findViewById(R.id.recyclerView);
        popularMovieRV = findViewById(R.id.popular_movie_rv);
        upComingMovieRV = findViewById(R.id.upcoming_movie_rv);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);


        configureRecyclerView();
        configurePopularMoveRecyclerView();
        configureUpcomingMoveRecyclerView();
        ObserveAnyChanges();
        ObservePopularMovies();
        ObserveUpcomingMovies();
        movieViewModel.searchPopularMovieApi(1);
        movieViewModel.searchUpcomingMovieApi(1);
    }



    private void configureUpcomingMoveRecyclerView() {
        upcomingMovieRVAdapter = new UpcomingMovieRVAdapter(this);
        upComingMovieRV.setAdapter(upcomingMovieRVAdapter);
        upComingMovieRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void configurePopularMoveRecyclerView() {

        popularMovieRVAdapter = new PopularMovieRVAdapter(this);
        popularMovieRV.setAdapter(popularMovieRVAdapter);
        popularMovieRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private void ObservePopularMovies() {

        movieViewModel.getPopularMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels!=null){

                    //movieRecyclerViewAdapter.setMovieModelList(movieModels);
                    popularMovieRVAdapter.setPopularMovieModelList(movieModels);

                }
            }
        });

    }

    private void ObserveUpcomingMovies() {

        movieViewModel.getUpcomingMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels!=null){
                    upcomingMovieRVAdapter.setUpcomingMovieModelList(movieModels);
                }
            }
        });

    }

    //observing any data change
    private void ObserveAnyChanges(){

        movieViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //observing of any changes in data

                if(movieModels!=null){

                    for(MovieModel movie : movieModels){
                        Log.v("Movies",movie.getTitle());
                    }
                    movieRecyclerViewAdapter.setMovieModelList(movieModels);

                }

            }
        });

    }

//    private void getRetrofitResponse() {
//
//        TMDBApi tmdbApi = SingletonService.getTmdbApi();
//
//        //
//
//        Call<MovieSearchResponse> responseCall = tmdbApi.searchMovie(ApiCredentials.API_KEY,"Love",2);
//
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//
//                if(response.code()==200){
//
//                    Log.v("Tag","Response from API: "+ response.body().toString());
//
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//
//                    for(MovieModel movie: movies){
//
//                        Log.v("Tag","Movie Name: "+movie.getTitle()+" Rating"+movie.getVote_average());
//
//                    }
//
//
//                }else{
//
//                    try {
//                        Log.v("Tag","Error: "+response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//                Log.v("Tag",t.toString());
//
//            }
//        });
//
//    }
//
//
//    // Search movie with id
//    private void getMovieById(){
//
//        TMDBApi tmdbApi = SingletonService.getTmdbApi();
//
//        Call<MovieModel> singleMovieResponseCall = tmdbApi.getMovie(343611, ApiCredentials.API_KEY);
//
//        singleMovieResponseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//
//                if(response.code() == 200){
//
//                    MovieModel movieModel = response.body();
//                    Log.v("SingleMovieResponse",movieModel.getTitle());
//                }else{
//
//                    try {
//                        Log.v("SingleMovieResponse",response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//
//
//    }

    //calling searchMovie in MainActivity
//    private void searchMovieApi(String query,int pageNumber){
//
//
//        Log.v("searchCall","from MainActivity query: "+query+" page: "+pageNumber);
//
//        movieViewModel.searchMovieApi(query,pageNumber);
//
//    }

    private void configureRecyclerView(){

        //Live data can not be pass via the constructor
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //RecyclerView Pagination
        //loading next page of api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){

                    movieViewModel.searchNextPage();

                }
            }
        });
    }

    @Override
    public void OnMovieClick(int position) {

        //Toast.makeText(this,"Position: "+position,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String Category) {

    }

    @Override
    public void OnPopularMovieClick(int position) {

        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie", popularMovieRVAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void OnUpcomingMovieClick(int position) {

        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie",upcomingMovieRVAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    //get Data From search view
    private void setUpSearchView(){

        final SearchView searchView = findViewById(R.id.sv_search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                recyclerView.setVisibility(View.VISIBLE);
                movieViewModel.searchMovieApi(query,1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });



    }
}