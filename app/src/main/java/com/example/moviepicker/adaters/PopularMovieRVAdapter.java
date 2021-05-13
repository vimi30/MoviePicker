package com.example.moviepicker.adaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.moviepicker.R;
import com.example.moviepicker.models.MovieModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularMovieRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> popularMovieModelList;
    private OnMovieClickListener movieClickListener;

    public PopularMovieRVAdapter(OnMovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie_list_item,parent,false);

        return new PopularMovieHolder(view,movieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((PopularMovieHolder)holder).rating.setRating(popularMovieModelList.get(position).getVote_average()/2);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+popularMovieModelList.get(position)
                        .getPoster_path()).into(((PopularMovieHolder)holder).img);


    }

    @Override
    public int getItemCount() {

        if(popularMovieModelList!=null){

            return popularMovieModelList.size();

        }
        return 0;

    }

    public void setPopularMovieModelList(List<MovieModel> movieModelList) {
        this.popularMovieModelList = movieModelList;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if(popularMovieModelList!=null){
            if(popularMovieModelList.size()>0){
                return popularMovieModelList.get(position);
            }
        }

        return null;
    }

}
