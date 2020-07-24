package com.master.projetmovies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.master.projetmovies.adapter.ActorAdapter
import com.master.projetmovies.adapter.StaffThumbnailAdapter
import com.master.projetmovies.model.CastAndStaff
import com.master.projetmovies.service.providers.NetworkListener
import com.master.projetmovies.service.providers.NetworkProvider


class ActorListActivity : AppCompatActivity() {

    private lateinit var actorRecyclerView: RecyclerView
    private lateinit var staffRecyclerView: RecyclerView
    private val actorAdapter by lazy { ActorAdapter() }
    private val staffAdapter by lazy { StaffThumbnailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_list)

        if (intent.hasExtra(MOVIE_ID)) {
            val movieId = intent.extras?.getLong(MOVIE_ID) ?: throw Exception()
            getData(movieId)

        }
        initView()


        actorRecyclerView.apply {
            adapter = actorAdapter

        }
        staffRecyclerView.apply {
            adapter = staffAdapter

        }
    }



    private fun initView() {
            actorRecyclerView = findViewById(R.id.list_actors_fragment)
            staffRecyclerView = findViewById(R.id.list_crew_fragment)

    }

    private fun getData(movieId: Long) {
        NetworkProvider.getCastingMovie(id=movieId,listener = object :
            NetworkListener<CastAndStaff> {

            override fun onError(throwable: Throwable) {
                Log.e("Error", throwable.localizedMessage)
            }

            override fun onSuccess(data: CastAndStaff) {

                    actorAdapter.actors=data.listCast
                    staffAdapter.staff=data.listStaff

            }
        })
    }

}