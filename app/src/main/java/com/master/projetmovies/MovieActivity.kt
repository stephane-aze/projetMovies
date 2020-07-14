package com.master.projetmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.master.projetmovies.adapter.ActorAdapter
import com.master.projetmovies.adapter.StaffAdapter
import com.master.projetmovies.databinding.ActivityMovieBinding
import com.master.projetmovies.model.CastAndStaff
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.providers.NetworkListener
import com.master.projetmovies.service.providers.NetworkProvider
import com.master.projetmovies.service.providers.movieIdInterstellar
//import io.reactivex.rxjava3.disposables.CompositeDisposable

const val MOVIE_ID = "LIST_ACTORS"
class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private var movie:Movie? = null
    private lateinit var actorRecyclerView: RecyclerView
    private lateinit var staffRecyclerView: RecyclerView
    private val actorAdapter by lazy { ActorAdapter() }
    private val staffAdapter by lazy { StaffAdapter() }

    override fun onResume() {
        super.onResume()
        //val compositeDisposable = CompositeDisposable()
        getDataMovie()
        getCastAndStaffMovie()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()
        initRecyclerViewActor()
        initRecyclerViewStaff()

        toRating()

        toGoListActor()
    }



    private fun getDataMovie(){

        NetworkProvider.getMovieInterstellar(listener = object : NetworkListener<Movie> {
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

    private fun setData() {
        with(binding.forefront) {
            movie?.let {
                titleMovie.text = it.title
                descriptionMovie.text = it.description
                scoreReviews.text = getString(R.string.score_reviews,it.metaScore)
                nbViews.text =getString(R.string.nb_views,it.nbView.toString())
                note.text = getString(R.string.note, it.note.toString())/*it.note.toString()*/
                nbReviews.text = getString(R.string.nb_reviews,it.nbReview.toString())
            }
        }
    }

    private fun getCastAndStaffMovie() {
        NetworkProvider.getCastingMovieInterstellar(listener = object : NetworkListener<CastAndStaff> {

            override fun onError(throwable: Throwable) {
                Log.e("Error", throwable.localizedMessage)
            }

            override fun onSuccess(data: CastAndStaff) {
                Log.d("AZE",data.toString())
                movie?.apply {
                    actorList=data.listCast
                    staffList=data.listStaff
                    setDataToRecyclerView()
                }
            }
        })
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
        var isAnimated = false
        buttonLikes.setOnClickListener {

            buttonLikes.speed = if(isAnimated) -2f else 3f
            buttonLikes.playAnimation()
            isAnimated=!isAnimated
        }
    }

    private fun toGoListActor() {
        binding.backfront.seeAll.setOnClickListener {
            val intent = Intent(this,ActorListActivity::class.java)
            intent.putExtra(MOVIE_ID, movieIdInterstellar)
            startActivity(intent)
        }
    }
}