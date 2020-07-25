package com.master.projetmovies.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master.projetmovies.MovieActivity
import com.master.projetmovies.R
import com.master.projetmovies.adapter.MoviesAdapter
import com.master.projetmovies.model.Movie
import java.util.*


class FavoritesFragment : Fragment() {

    private lateinit var spinner: Spinner
    private val searchAdapter by lazy { MoviesAdapter() }
    private lateinit var listMoviesView: RecyclerView
    private lateinit var searchEdit: EditText
    private var page=1
    private val database = Firebase.database
    private lateinit var auth: FirebaseAuth
    private var listAllFavorites = mutableListOf<Movie>()
    private lateinit var searchMovieView: SearchView

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
        auth = Firebase.auth
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initSpinner()
        listMoviesView.adapter = searchAdapter
        searchAdapter.movieListener = { showMovie(it) }
        getAllFavorites()
        searchMovie(view)
    }

    private fun getAllFavorites() {
        val reference =
            database.reference.child("users").child(auth.currentUser!!.uid).child("favorites")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
            listAllFavorites= mutableListOf()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val p: Movie = dataSnapshot1.getValue(Movie::class.java)!!

                    listAllFavorites.add(p)
                }
                searchAdapter.movies = listAllFavorites
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    activity,
                    "Opsss.... Something is wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initSpinner() {
        val itemsSpinner = arrayListOf(getString(R.string.year))
        val yearCurrent = Calendar.getInstance().get(Calendar.YEAR)
        for (x in yearCurrent downTo 1988) {
            itemsSpinner.add(x.toString())

        }

        val arrayAdapter =
            ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, itemsSpinner)
        arrayAdapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position > 0) {
                    val textSelected = itemsSpinner[position]
                    searchByYears(textSelected)

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }
    private fun searchMovie(view: View) {

        searchMovieView=view.findViewById(R.id.edit_search)
        searchMovieView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return false
            }

        })


    }
    private fun search(s: String?) {
        searchAdapter.movies=listAllFavorites.filter {
            it.title.toLowerCase(
                Locale.ROOT).contains(s!!.toLowerCase(
                Locale.ROOT))
        } as MutableList<Movie>
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
        if(s.isNotBlank()){
            searchAdapter.movies=searchAdapter.movies.filter {
                it.year.toString().contains(s)
            } as MutableList<Movie>

        }
    }

}