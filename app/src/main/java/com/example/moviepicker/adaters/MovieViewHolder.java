package com.example.moviepicker.adaters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviepicker.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    TextView title, releaseDate, original_language;
    ImageView imageView;
    RatingBar ratingBar;

    //Click Listener
    OnMovieClickListener movieClickListener;


    public MovieViewHolder(@NonNull View itemView, OnMovieClickListener movieClickListener) {
        super(itemView);

        this.movieClickListener = movieClickListener;
        title = itemView.findViewById(R.id.movie_title);
        releaseDate = itemView.findViewById(R.id.movie_release_date);
        original_language = itemView.findViewById(R.id.movie_duration);
        imageView = itemView.findViewById(R.id.iv_movie_poster);
        ratingBar = itemView.findViewById(R.id.movie_rating);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        movieClickListener.OnMovieClick(getAdapterPosition());

    }
}
