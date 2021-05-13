package com.example.moviepicker.adaters;

public interface OnMovieClickListener {

    void OnMovieClick(int position);
    void OnPopularMovieClick(int position);
    void OnUpcomingMovieClick(int position);
    void onCategoryClick(String Category);
}
