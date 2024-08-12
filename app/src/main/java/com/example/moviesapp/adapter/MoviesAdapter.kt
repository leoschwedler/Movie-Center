package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.model.Movies
import com.example.moviesapp.util.Const
import com.squareup.picasso.Picasso

class MoviesAdapter(val onClick: (Movies) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var listBanner = mutableListOf<Movies>()

    fun addList(list: List<Movies>) {
        listBanner.addAll(list)
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movies) {
            binding.textViewTitle.text = movies.title
            val movieBackdropPath = movies.backdrop_path
            val imageSize = "original"
            val baseUrl = Const.BASE_URL_IMAGE
            val imageUrl = "$baseUrl$imageSize$movieBackdropPath"
            Picasso.get()
                .load(imageUrl)
                .into(binding.imageViewMovie)
            binding.movieItem.setOnClickListener {
                onClick(movies)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MoviesViewHolder(view)
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val filmesTopRated = listBanner[position]
        holder.bind(filmesTopRated)
    }

    override fun getItemCount(): Int = listBanner.size

}