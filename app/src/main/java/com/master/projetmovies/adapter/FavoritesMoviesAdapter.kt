package com.master.projetmovies.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.master.projetmovies.R
import com.master.projetmovies.misc.inflate
import com.master.projetmovies.model.Actor

class FavoritesMoviesAdapter: RecyclerView.Adapter<ActorViewHolder>() {

    var actors: List<Actor> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
/*        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weapon, parent, false)*/
        return ActorViewHolder(
            parent.inflate(
                R.layout.item_actor
            )
        )
    }
    override fun getItemCount() = actors.size
    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        //val itemList = dataList.get(position) --> Gladiator
        //holder description de ma vue
        val actor = actors[position]
        holder.bindData(actor)

    }
}
