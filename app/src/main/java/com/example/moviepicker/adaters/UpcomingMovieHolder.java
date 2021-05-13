package com.example.moviepicker.adaters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviepicker.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imgUpcoming;
    TextView upcomingReleaseDate;
    OnMovieClickListener movieClickListener;

    public UpcomingMovieHolder(@NonNull View itemView,OnMovieClickListener movieClickListener) {
        super(itemView);
        this.movieClickListener = movieClickListener;
        imgUpcoming = itemView.findViewById(R.id.mImg_upcoming);
        upcomingReleaseDate = itemView.findViewById(R.id.upcoming_date);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        movieClickListener.OnUpcomingMovieClick(getAdapterPosition());

    }
}
