package com.master.projetmovies.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.master.projetmovies.MovieActivity
import com.master.projetmovies.R
import com.master.projetmovies.adapter.MovieThumbnailAdapter
import com.master.projetmovies.adapter.MoviesAdapter
import com.master.projetmovies.databinding.FragmentMoviesBinding
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.providers.NetworkListener
import com.master.projetmovies.service.providers.NetworkProvider


class MoviesFragment : Fragment() {
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private val popularMoviesAdapter by lazy { MovieThumbnailAdapter() }
    private var popularMoviesPage = 1
    private lateinit var topRatedRecyclerView: RecyclerView
    private val topRatedAdapter by lazy { MovieThumbnailAdapter() }
    private var topRatedPage = 1
    private lateinit var upcomingRecyclerView: RecyclerView
    private val upcomingAdapter by lazy { MovieThumbnailAdapter() }
    private var upcomingPage = 1
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var popularMoviesLayoutManager: LinearLayoutManager

    override fun onResume() {
        super.onResume()
        getPopularMovies()
        getTopRated()
        getUpcoming()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        popularMoviesRecyclerView = binding.listPopularMovies
        popularMoviesLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMoviesRecyclerView.apply {
            adapter = popularMoviesAdapter
            //layoutManager = popularMoviesLayoutManager

        }
        topRatedRecyclerView = binding.topRatedMovies
        /*popularMoviesLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )*/
        topRatedRecyclerView.apply {
            adapter = topRatedAdapter
            //layoutManager = popularMoviesLayoutManager

        }
        upcomingRecyclerView = binding.upcomingMovies
        upcomingRecyclerView.apply {
            adapter = upcomingAdapter
            //layoutManager = popularMoviesLayoutManager

        }
        eventAdapter()
    }

    private fun eventAdapter() {
        popularMoviesAdapter.movieListener = { showMovie(it) }
        topRatedAdapter.movieListener = { showMovie(it) }
        upcomingAdapter.movieListener = { showMovie(it) }
    }

    private fun showMovie(movie: Movie) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra("movieId",movie.id)
        startActivity(intent)
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutManager.itemCount
                val visibleItemCount = popularMoviesLayoutManager.childCount
                val firstVisibleItem = popularMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMoviesRecyclerView.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }
    private fun getPopularMovies() {
        NetworkProvider.getPopularMovies(popularMoviesPage,listener = object: NetworkListener<List<Movie>>{
            override fun onSuccess(data: List<Movie>) {
                popularMoviesAdapter.movies=data as MutableList<Movie>
                //attachPopularMoviesOnScrollListener()
            }

            override fun onError(throwable: Throwable) {
                Log.e("Error", throwable.localizedMessage)
            }
        })

    }
    private fun getTopRated() {
        NetworkProvider.getTopRatedMovies(topRatedPage,listener = object: NetworkListener<List<Movie>>{
            override fun onSuccess(data: List<Movie>) {
                topRatedAdapter.movies=data as MutableList<Movie>
            }

            override fun onError(throwable: Throwable) {
                Log.e("Error", throwable.localizedMessage)
            }
        })

    }
    private fun getUpcoming() {
        NetworkProvider.getUpcomingMovies(upcomingPage,listener = object: NetworkListener<List<Movie>>{
            override fun onSuccess(data: List<Movie>) {
                upcomingAdapter.movies=data as MutableList<Movie>
            }

            override fun onError(throwable: Throwable) {
                Log.e("Error", throwable.localizedMessage)
            }
        })

    }
}