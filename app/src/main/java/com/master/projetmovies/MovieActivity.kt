package com.master.projetmovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master.projetmovies.adapter.ActorAdapter
import com.master.projetmovies.adapter.StaffAdapter
import com.master.projetmovies.databinding.ActivityMovieBinding
import com.master.projetmovies.model.CastAndStaff
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.providers.NetworkListener
import com.master.projetmovies.service.providers.NetworkProvider


//import io.reactivex.rxjava3.disposables.CompositeDisposable

const val MOVIE_ID = "LIST_ACTORS"

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private var movie:Movie? = null
    private lateinit var actorRecyclerView: RecyclerView
    private lateinit var staffRecyclerView: RecyclerView
    private val actorAdapter by lazy { ActorAdapter() }
    private val staffAdapter by lazy { StaffAdapter() }
    private  var movieId: Long? =null
    private lateinit var auth: FirebaseAuth

    /*override fun onResume() {
        super.onResume()
        //val compositeDisposable = CompositeDisposable()
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (intent.hasExtra("movieId")) {

            movieId=bundle?.getLong("movieId")?: throw Exception()
        }
        auth = Firebase.auth
        getDataMovie()
        getCastAndStaffMovie()


        initView()
        initRecyclerViewActor()
        initRecyclerViewStaff()

        toRating()

        toGoListActor()
    }



    private fun getDataMovie(){
        movieId?.let {
            NetworkProvider.getMovie(id = it,listener = object : NetworkListener<Movie> {
                // associer ma liste de weapon à mon adapter :  weaponsAdapter.weapons = data
                override fun onSuccess(data: Movie) {

                    movie=data
                    setData()

                }

                override fun onError(throwable: Throwable) {
                    Log.e("Error", throwable.localizedMessage)
                }
            })

        }

    }

    private fun setData() {
        with(binding.forefront) {
            movie?.let {
                durationMovie.text = it.duration
                titleMovie.text = it.title
                descriptionMovie.text = it.description
                scoreReviews.text = getString(R.string.score_reviews,it.metaScore)
                nbViews.text =getString(R.string.nb_views,it.nbView.toString())
                note.text = getString(R.string.note, it.note.toString())/*it.note.toString()*/
                nbReviews.text = getString(R.string.nb_reviews,it.nbReview.toString())
                yearMovie.text = it.year.toString()
                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w500${it.imageUrl}")
                    .placeholder(R.drawable.img_interstellar)
                    .into(binding.forefront.imageMovie)

            }
        }
    }

    private fun getCastAndStaffMovie() {
        movieId?.let {
            NetworkProvider.getCastingMovie(
                id = it,
                listener = object : NetworkListener<CastAndStaff> {

                    override fun onError(throwable: Throwable) {
                        Log.e("Error", throwable.localizedMessage)
                    }

                    override fun onSuccess(data: CastAndStaff) {
                        Log.d("AZE", data.toString())
                        movie?.apply {
                            actorList = data.listCast
                            staffList = data.listStaff
                            setDataToRecyclerView()
                        }
                    }
                })
        }
    }

    private fun initView() {
            actorRecyclerView = binding.backfront.listActors
            staffRecyclerView =binding.backfront.listProduction

    }

    private fun initRecyclerViewActor() {
        actorRecyclerView.apply {
            adapter = actorAdapter
        }

    }

    private fun initRecyclerViewStaff() {
        staffRecyclerView.apply {
            adapter = staffAdapter
        }
    }
    private fun setDataToRecyclerView() {
        movie?.apply {
            actorAdapter.actors=actorList?: emptyList()
            staffAdapter.staff=staffList?: emptyList()

        }
    }

    private fun toRating() {
        val buttonLikes = binding.forefront.buttonLike
        val database = Firebase.database.reference

        var isAnimated = false
        database.child("users").child(auth.currentUser!!.uid).child("favorites").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Err has child",error.details)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("arfefef",snapshot.child(movieId.toString()).exists().toString())

                isAnimated=snapshot.child(movieId.toString()).exists()
                buttonLikes.progress= if(isAnimated)1f else 0f


            }
        })
        buttonLikes.setOnClickListener {

            buttonLikes.speed = if(isAnimated)-3f else 3f
            buttonLikes.playAnimation()
            isAnimated=!isAnimated
            if(isAnimated){
                auth.currentUser?.let {

                    database.child("users").child(auth.currentUser!!.uid).child("favorites").child(movieId.toString()).setValue(movie).addOnFailureListener { Log.d("eeerrr","nooooonn") }
                }
            }else{
                database.child("users").child(auth.currentUser!!.uid).
                child("favorites").child(movieId.toString()).removeValue()
            }
        }
    }

    private fun toGoListActor() {
        binding.backfront.seeAll.setOnClickListener {
            val intent = Intent(this,ActorListActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            startActivity(intent)
        }
    }
}