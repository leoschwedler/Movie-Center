package com.example.moviesapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.databinding.ActivityDetailBinding
import com.example.moviesapp.model.Movies
import com.example.moviesapp.util.Const
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        handleMovieExtras()

        binding.imageViewBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleMovieExtras() {
        val bundle = intent.extras
        if (bundle != null){
            val movie = bundle.getParcelable("Movie", Movies::class.java)
            if (movie != null){
                val movieBackdropPath = movie.backdrop_path
                val imageSize = "original"
                val baseUrl = Const.BASE_URL_IMAGE
                val imageUrl = "$baseUrl$imageSize$movieBackdropPath"

                Picasso.get().load(imageUrl).into(binding.imageViewMovie)
                binding.textViewTitle.text = movie.title
                binding.textViewOverview.text = movie.overview
            }
        }
    }
}


