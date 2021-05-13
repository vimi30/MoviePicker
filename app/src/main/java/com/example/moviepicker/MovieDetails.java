package com.example.moviepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviepicker.models.MovieModel;

public class MovieDetails extends AppCompatActivity {

    private ImageView moviePosterDetail;
    private RatingBar movieRatingDetail;
    private TextView movieTitleDetail, movieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        moviePosterDetail = findViewById(R.id.imageViewDetails);
        movieRatingDetail = findViewById(R.id.ratingBar_details);
        movieTitleDetail = findViewById(R.id.tv_movie_title);
        movieOverview = findViewById(R.id.tv_overview);
        
        getDataFromIntent();
    }

    private void getDataFromIntent() {

        if(getIntent().hasExtra("movie")){

            MovieModel movie = getIntent().getParcelableExtra("movie");
            //Log.v("itemCliecked","Incoming Intent "+movie.getMovie_id());
            movieTitleDetail.setText(movie.getTitle());
            movieOverview.setText(movie.getOverview());
            movieRatingDetail.setRating(movie.getVote_average()/2);

            Glide.with(this).load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()).into(moviePosterDetail);

        }

    }
}