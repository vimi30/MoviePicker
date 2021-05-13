package com.example.moviepicker.adaters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.moviepicker.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    RatingBar rating;
    OnMovieClickListener movieClickListener;

    public PopularMovieHolder(@NonNull View itemView,OnMovieClickListener onMovieClickListener) {
        super(itemView);

        this.movieClickListener = onMovieClickListener;
        img = itemView.findViewById(R.id.mImg);
        rating = itemView.findViewById(R.id.ratingBar);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        movieClickListener.OnPopularMovieClick(getAdapterPosition());
    }
}
