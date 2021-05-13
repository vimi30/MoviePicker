package com.example.moviepicker.adaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.moviepicker.R;
import com.example.moviepicker.models.MovieModel;
import com.example.moviepicker.viewModels.MovieViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> movieModelList;
    private OnMovieClickListener movieClickListener;

    public MovieRecyclerViewAdapter(OnMovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moive_list_item,parent,false);

        return new MovieViewHolder(view,movieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MovieViewHolder)holder).title.setText(movieModelList.get(position).getTitle());
        ((MovieViewHolder)holder).releaseDate.setText(movieModelList.get(position).getRelease_date());
        ((MovieViewHolder)holder).original_language.setText(movieModelList.get(position).getOriginal_language());
        ((MovieViewHolder)holder).ratingBar.setRating(movieModelList.get(position).getVote_average()/2);

        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500"+movieModelList.get(position).getBackdrop_path()).into(((MovieViewHolder)holder).imageView);


    }

    @Override
    public int getItemCount() {

        if(movieModelList!=null){
            return movieModelList.size();
        }

        return 0;

    }

    public void setMovieModelList(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
        notifyDataSetChanged();
    }

    //getting id of the movie clicked
    public MovieModel getSelectedMovie(int position){
        if(movieModelList!=null){
            if(movieModelList.size()>0){
                return movieModelList.get(position);
            }
        }

        return null;
    }
}
