package com.master.projetmovies.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.master.projetmovies.MovieActivity
import com.master.projetmovies.R
import com.master.projetmovies.adapter.MoviesAdapter
import com.master.projetmovies.databinding.FragmentMoviesBinding
import com.master.projetmovies.databinding.FragmentSearchBinding
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.providers.NetworkListener
import com.master.projetmovies.service.providers.NetworkProvider
import java.util.*

class SearchFragment : Fragment() {
    private lateinit var spinner: Spinner
    private val searchAdapter by lazy { MoviesAdapter() }
    private lateinit var listMoviesView: RecyclerView
    private lateinit var searchEdit: EditText
    private lateinit var binding: FragmentSearchBinding
    private var page=1
    override fun onResume() {
        super.onResume()
        getMovies()
    }
    private fun getMovies() {
        NetworkProvider.getPopularMovies(page = page,listener = object:
            NetworkListener<List<Movie>> {
            override fun onSuccess(data: List<Movie>) {
                searchAdapter.movies=data as MutableList<Movie>
                Log.d("search ", searchAdapter.movies.toString())
            }

            override fun onError(throwable: Throwable) {
                Log.d("search ", "errror")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initView(view)
        initSpinner()
        searchMovie()
        listMoviesView.adapter = searchAdapter
        searchAdapter.movies= mutableListOf()
        searchAdapter.movieListener = { showMovie(it) }


    }

    private fun initSpinner() {
        val yearCurrent= Calendar.getInstance().get(Calendar.YEAR)
        val itemsSpinner= arrayListOf(getString(R.string.year))
        for(x in  yearCurrent downTo  1988){
            itemsSpinner.add(x.toString())

        }

        val arrayAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, itemsSpinner)
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner.adapter=arrayAdapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if(position > 0){
                    val textSelected = itemsSpinner[position]
                    searchByYears(textSelected)

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    private fun searchMovie() {
        binding.editSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    search(it)

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    search(it)

                }
                return false
            }

        })
    }

    private fun search(query: String) {
        if(query.isNotBlank()){
            NetworkProvider.searchMovies(query = query,listener = object : NetworkListener<List<Movie>>{
                override fun onSuccess(data: List<Movie>) {
                    searchAdapter.movies=data as MutableList<Movie>
                }

                override fun onError(throwable: Throwable) {
                    Log.e("Error", throwable.localizedMessage)
                }

            })

        }
    }

    private fun initView(view: View) {
        //chipGroup = view.findViewById(R.id.chip_group)
        spinner = view.findViewById(R.id.spinner_years)
        listMoviesView= view.findViewById(R.id.listMoviesView)

    }
    private fun showMovie(movie: Movie) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra("movieId",movie.id)
        startActivity(intent)
    }
    private fun searchByYears(s: String) {
        NetworkProvider.searchMoviesByYear(query = s,listener = object : NetworkListener<List<Movie>>{
            override fun onSuccess(data: List<Movie>) {
                searchAdapter.movies=data as MutableList<Movie>
            }

            override fun onError(throwable: Throwable) {
                Log.e("Error", throwable.localizedMessage)
            }

        })

    }


}