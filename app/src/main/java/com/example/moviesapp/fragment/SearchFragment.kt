package com.example.moviesapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.adapter.MoviesAdapter
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.databinding.FragmentSearchBinding
import com.example.moviesapp.model.MoviesResponse
import com.example.moviesapp.view.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchFragment : Fragment() {

    private val theMovieDBAPI by lazy { RetrofitService.theMovieDB }
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        setUpSearch()
    }

    private fun initializeView() {
        moviesAdapter = MoviesAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Movie", it)
            startActivity(intent)
        }
        binding.recyclerViewSearchResults.adapter = moviesAdapter
        binding.recyclerViewSearchResults.layoutManager = GridLayoutManager(context, 3)
    }

    private fun setUpSearch() {
        binding.editTextSearchMovies.setOnClickListener {
            val query = binding.editTextSearchMovies.text.toString()
            if (query.isNotEmpty()) {
                searchMovies(query)
            }
        }
    }

    private fun searchMovies(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<MoviesResponse>? = null
            try {
                response = theMovieDBAPI.searchMovies(query)
            } catch (e: Exception) {
                Log.i("api_error", "Error making the request: ${e.message}")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    val moviesResponse = response.body()
                    val movieList = moviesResponse?.Movies
                    withContext(Dispatchers.Main) {
                        if (movieList != null && movieList.isNotEmpty()) {
                            moviesAdapter.addList(movieList)
                        } else {
                            moviesAdapter.addList(emptyList())
                        }
                    }
                } else {
                    Log.i("api_error", "Problem making the request CODE: ${response.code()}")
                }
            } else {
                Log.i("api_error", "Unable to make the request")
            }
        }
    }
}
