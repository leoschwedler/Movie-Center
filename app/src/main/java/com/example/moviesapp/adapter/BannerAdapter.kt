package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ItemBannerBinding
import com.example.moviesapp.model.Movies
import com.example.moviesapp.util.Const
import com.squareup.picasso.Picasso

class BannerAdapter(val onClick: (Movies) -> Unit) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var listBanner = mutableListOf<Movies>()

    fun addList(list: List<Movies>) {
        listBanner.addAll(list)
        notifyDataSetChanged()
    }

    inner class BannerViewHolder(val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movies) {
            binding.textViewTitle.text = movies.title
            val movieBackdropPath = movies.backdrop_path
            val imageSize = "original"
            val baseUrl = Const.BASE_URL_IMAGE
            val imageUrl = "$baseUrl$imageSize$movieBackdropPath"
            Picasso.get()
                .load(imageUrl)
                .into(binding.imageViewBanner)
            binding.bannerItem.setOnClickListener {
                onClick(movies)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemBannerBinding.inflate(layoutInflater, parent, false)
        return BannerViewHolder(view)
    }


    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val filmesTopRated = listBanner[position]
        holder.bind(filmesTopRated)
    }

    override fun getItemCount(): Int = listBanner.size

}