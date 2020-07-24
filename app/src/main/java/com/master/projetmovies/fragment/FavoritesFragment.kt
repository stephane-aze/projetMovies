package com.master.projetmovies.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master.projetmovies.MovieActivity
import com.master.projetmovies.R
import com.master.projetmovies.adapter.MoviesAdapter
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.providers.NetworkListener
import com.master.projetmovies.service.providers.NetworkProvider
import java.util.*


class FavoritesFragment : Fragment() {

    private lateinit var spinner: Spinner
    private val searchAdapter by lazy { MoviesAdapter() }
    private lateinit var listMoviesView: RecyclerView
    private lateinit var searchEdit: EditText
    private var page=1
    val database = Firebase.database



    override fun onResume() {
        super.onResume()
        Log.d("search1 ", "errror")

        getMovies()
    }

    private fun getMovies() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        val itemsSpinner= arrayListOf(getString(R.string.year))
        val yearCurrent= Calendar.getInstance().get(Calendar.YEAR)
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
        listMoviesView.adapter = searchAdapter
        searchAdapter.movieListener = { showMovie(it) }
        /*searchAdapter. = { movieItem: Movie ->
            partItemClicked(movieItem)
        }
        searchAdapter.listItem = listMovies
        searchMovie(view)*/
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
        /*if(s.isNotBlank()){
            searchAdapter.movies=searchAdapter.movies.filter {
                it.releaseDate.contains(s)
            }

        }else{
            searchAdapter.listItem=listMovies
        }*/
    }

}