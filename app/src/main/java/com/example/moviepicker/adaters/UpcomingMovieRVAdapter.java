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

public class UpcomingMovieRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> upcomingMovieModelList;
    private OnMovieClickListener movieClickListener;

    public UpcomingMovieRVAdapter(OnMovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_movie_item,parent,false);

        return new UpcomingMovieHolder(view,movieClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((UpcomingMovieHolder)holder).upcomingReleaseDate.setText(upcomingMovieModelList.get(position).getRelease_date());

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+upcomingMovieModelList.get(position)
                        .getPoster_path()).into(((UpcomingMovieHolder)holder).imgUpcoming);

    }

    @Override
    public int getItemCount() {

        if(upcomingMovieModelList!=null){

            return upcomingMovieModelList.size();

        }
        return 0;

    }

    public void setUpcomingMovieModelList(List<MovieModel> movieModelList) {
        this.upcomingMovieModelList = movieModelList;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if(upcomingMovieModelList!=null){
            if(upcomingMovieModelList.size()>0){
                return upcomingMovieModelList.get(position);
            }
        }

        return null;
    }


}
