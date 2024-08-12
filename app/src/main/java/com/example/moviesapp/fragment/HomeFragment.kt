package com.example.moviesapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapter.BannerAdapter
import com.example.moviesapp.adapter.MoviesAdapter
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.model.MoviesResponse
import com.example.moviesapp.view.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HomeFragment : Fragment() {

    private val theMovieDBAPI by lazy { RetrofitService.theMovieDB }
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentHomeBinding
    private var pageNow = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBanner()
        initializePopularMovies()
        initializeUpcomingMovies()

    }

    private fun initializeUpcomingMovies() {
        upcomingMoviesAdapter = MoviesAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Movie", it)
            startActivity(intent)
        }
        binding.RecyclerViewUpcomingMovies.adapter = upcomingMoviesAdapter
        binding.RecyclerViewUpcomingMovies.layoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL, false)
        fetchUpcomingMovies()
        setupScrollListener(binding.RecyclerViewUpcomingMovies, ::fetchUpcomingMovies)
    }

    private fun initializePopularMovies() {
        popularMoviesAdapter = MoviesAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Movie", it)
            startActivity(intent)
        }
        binding.recyclerViewPopularMovies.adapter = popularMoviesAdapter
        binding.recyclerViewPopularMovies.layoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL, false)
        fetchPopularMovies()
        setupScrollListener(binding.recyclerViewPopularMovies, ::fetchPopularMovies)
    }

    private fun initializeBanner() {
        bannerAdapter = BannerAdapter {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Movie", it)
            startActivity(intent)
        }
        binding.ViewPager.adapter = bannerAdapter
        fetchTopRatedMovies()
    }

    private fun fetchUpcomingMovies() {
        val jobUpcomingMovies = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<MoviesResponse>? = null
            try {
                response = theMovieDBAPI.getUpcomingMovies(pageNow)
            } catch (e: Exception) {
                Log.i("api_error", "Error making the request: ${e.message}")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    val upcomingMovies = response.body()
                    val movieList = upcomingMovies?.Movies
                    if (movieList != null && movieList.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            upcomingMoviesAdapter.addList(movieList)
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

    private fun fetchPopularMovies() {
        val jobPopularMovies = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<MoviesResponse>? = null
            try {
                response = theMovieDBAPI.getPopularMovies(pageNow)
            } catch (e: Exception) {
                Log.i("api_error", "Error making the request: ${e.message}")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    val popularMoviesResponse = response.body()
                    val movieList = popularMoviesResponse?.Movies
                    if (movieList != null && movieList.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            popularMoviesAdapter.addList(movieList)
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

    private fun fetchTopRatedMovies() {
        val job = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<MoviesResponse>? = null
            try {
                response = theMovieDBAPI.getTopRatedMovies(pageNow)
            } catch (e: Exception) {
                Log.i("api_error", "Error making the request: ${e.message}")
            }
            if (response != null) {
                if (response.isSuccessful) {
                    val topRatedMoviesResponse = response.body()
                    val movieList = topRatedMoviesResponse?.Movies
                    if (movieList != null && movieList.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            bannerAdapter.addList(movieList)
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

    private fun setupScrollListener(recyclerView: RecyclerView, fetchMovies: () -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Check if reached the end of the list
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    getNextPage()
                    fetchMovies()
                }
            }
        })
    }

    private fun getNextPage() {
        if (pageNow < 1000) {
            pageNow++
        }
    }
}
